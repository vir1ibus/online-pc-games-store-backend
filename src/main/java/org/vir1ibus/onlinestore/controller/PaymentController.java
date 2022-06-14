package org.vir1ibus.onlinestore.controller;

import com.qiwi.billpayments.sdk.client.BillPaymentClient;
import com.qiwi.billpayments.sdk.client.BillPaymentClientFactory;
import com.qiwi.billpayments.sdk.exception.BillPaymentServiceException;
import com.qiwi.billpayments.sdk.model.BillStatus;
import com.qiwi.billpayments.sdk.model.MoneyAmount;
import com.qiwi.billpayments.sdk.model.in.PaymentInfo;
import com.qiwi.billpayments.sdk.model.out.BillResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vir1ibus.onlinestore.database.entity.*;
import org.vir1ibus.onlinestore.database.repository.ActivateKeyRepository;
import org.vir1ibus.onlinestore.database.repository.AuthorizationTokenRepository;
import org.vir1ibus.onlinestore.database.repository.BasketRepository;
import org.vir1ibus.onlinestore.database.repository.PurchaseRepository;
import org.vir1ibus.onlinestore.service.EmailService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final AuthorizationTokenRepository authorizationTokenRepository;
    private final PurchaseRepository purchaseRepository;
    private final BasketRepository basketRepository;
    private final ActivateKeyRepository activateKeyRepository;
    private final EmailService emailService;

    public PaymentController(AuthorizationTokenRepository authorizationTokenRepository,
                             PurchaseRepository purchaseRepository,
                             BasketRepository basketRepository,
                             ActivateKeyRepository activateKeyRepository,
                             EmailService emailService) {
        this.authorizationTokenRepository = authorizationTokenRepository;
        this.purchaseRepository = purchaseRepository;
        this.basketRepository = basketRepository;
        this.activateKeyRepository = activateKeyRepository;
        this.emailService = emailService;
    }

    private final String publicKey = "48e7qUxn9T7RyYE1MVZswX1FRSbE6iyCj2gCRwwF3Dnh5XrasNTx3BGPiMsyXQFNKQhvukniQG8RT" +
            "VhYm3iPwJmWXF5cY7uu215ogrg9ZpH95YoEZigoQgq9Yi3kGtjEb6kFNgfuz8ZcZaYhm7u3tk4Yctt5ztzgrzwFcstnua1BcW2AtA2" +
            "uZVRcLhXAM";
    private final String secretKey = "eyJ2ZXJzaW9uIjoiUDJQIiwiZGF0YSI6eyJwYXlpbl9tZXJjaGFudF9zaXRlX3VpZCI6InM4dmRx" +
            "OS0wMCIsInVzZXJfaWQiOiI3OTMwODA5ODg4MiIsInNlY3JldCI6ImFmZGI5ZGZlYTBhNTkwODY1YjYyYjdkYTExNWQzMTg0ZWJhZ" +
            "TlhZGRkMTA2ZTU1MzJlY2EzNmY3NmRkZGQ4ZTYifX0=";
    private final BillPaymentClient client = BillPaymentClientFactory.createDefault(secretKey);

    private User isAuthenticated(String token) throws NullPointerException, NoSuchElementException {
        AuthorizationToken authorizationToken = authorizationTokenRepository.findTokenByValue(token);
        if(authorizationToken.getActive() && authorizationToken.getUser().getActive()) {
            return authorizationToken.getUser();
        } else {
            throw new NullPointerException();
        }
    }

    /*
        Осуществляет получение информации о корзине пользователя, создаёт счёт во внешней системе QIWI
        возвращает ссылку для перехода на страницу оплаты.
     */

    @RequestMapping(value = "/buy/items", method = RequestMethod.GET)
    public ResponseEntity<?> purchaseItem(@RequestHeader(value = "Authorization") String token,
                                          HttpServletRequest httpServletRequest) {
        try {
            User buyer = isAuthenticated(token);
            Basket basket = buyer.getBasket();
            Set<Item> purchasedItems = basket.getItems();
            Integer sumPurchase = 0;
            for (Item item : purchasedItems) {
                if (item.getDiscount() > 0) {
                    sumPurchase += item.getPrice() - (item.getPrice() / 100 * item.getDiscount());
                } else {
                    sumPurchase += item.getPrice();
                }
            }

            String billId = UUID.randomUUID().toString();

            Purchase purchase = purchaseRepository.save(Purchase.builder()
                    .buyer(buyer)
                    .items(Set.copyOf(purchasedItems))
                    .sum(sumPurchase)
                    .billId(billId)
                    .build());
            basket.getItems().clear();
            basketRepository.save(basket);

            PaymentInfo paymentInfo = new PaymentInfo(
                    publicKey,
                    new MoneyAmount(
                            BigDecimal.valueOf(sumPurchase),
                            Currency.getInstance("RUB")
                    ),
                    billId,
                    httpServletRequest.getHeader("referer") + "/payment/success?billId=" + billId
            );
            return ResponseEntity.ok(client.createPaymentForm(paymentInfo));
        } catch (NullPointerException | NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /*
    Метод проверяющий статус платежа, в случае успеха отправляет на электронную почту пользователя
    ключи активации для купленных продуктов.
     */

    @RequestMapping(value = "/success", method = RequestMethod.GET)
    public ResponseEntity<?> successPurchase(@RequestHeader(value = "Authorization") String token, @RequestParam String billId) {
        try {
            User buyer = isAuthenticated(token);
            Purchase purchase = purchaseRepository.getPurchaseByBillIdAndBuyer(billId, isAuthenticated(token));
            BillResponse response = client.getBillInfo(billId);
            if(response.getStatus().getValue() == BillStatus.PAID && !purchase.getPaid()) {
                purchase.setPaid(true);
                Set<Item> purchasedItems = purchase.getItems();
                for(Item item : purchasedItems) {
                    ActivateKey activateKey = activateKeyRepository.findFirstByItemAndPurchaseIsNull(item);
                    activateKey.setPurchase(purchase);
                    activateKeyRepository.save(activateKey);
                    purchase.getActivateKeys().add(activateKey);
                }
                purchaseRepository.save(purchase);
                emailService.sendActivateKeys(buyer.getEmail(), purchase, purchase.getActivateKeys());
                return new ResponseEntity<>(HttpStatus.OK);
            } else if(response.getStatus().getValue() == BillStatus.WAITING){
                return new ResponseEntity<>(HttpStatus.ACCEPTED);
            } else if(response.getStatus().getValue() == BillStatus.EXPIRED || response.getStatus().getValue() == BillStatus.REJECTED) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NullPointerException | NoSuchElementException | BillPaymentServiceException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
