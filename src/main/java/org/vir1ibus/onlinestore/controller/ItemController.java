package org.vir1ibus.onlinestore.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.vir1ibus.onlinestore.database.entity.*;
import org.vir1ibus.onlinestore.database.repository.*;
import org.vir1ibus.onlinestore.database.specification.ItemSpecification;
import org.vir1ibus.onlinestore.utils.Cryptography;
import org.vir1ibus.onlinestore.utils.CustomPage;
import org.vir1ibus.onlinestore.utils.JSONConverter;
import org.vir1ibus.onlinestore.utils.StringFormatter;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import java.net.URI;
import java.util.*;

@RestController
@RequestMapping("/")
public class ItemController {

    private final UserRepository userRepository;
    private final AuthorizationTokenRepository authorizationTokenRepository;
    private final ItemRepository itemRepository;
    private final GenreRepository genreRepository;
    private final BasketRepository basketRepository;
    private final Integer ITEMS_ON_PAGE = 15;
    private final PublisherRepository publisherRepository;
    private final DeveloperRepository developerRepository;
    private final PurchaseRepository purchaseRepository;
    private final ReviewRepository reviewRepository;
    private final RoleRepository roleRepository;
    private final ItemTypeRepository itemTypeRepository;
    private final RegionActivationRepository regionActivationRepository;
    private final ServiceActivationRepository serviceActivationRepository;
    private final SystemRequirementRepository systemRequirementRepository;

    public ItemController(UserRepository userRepository, ItemRepository itemRepository, GenreRepository genreRepository,
                          AuthorizationTokenRepository authorizationTokenRepository, BasketRepository basketRepository, PublisherRepository publisherRepository, DeveloperRepository developerRepository, ActivateKeyRepository activateKeyRepository, PurchaseRepository purchaseRepository, ReviewRepository reviewRepository, RoleRepository roleRepository, ItemTypeRepository itemTypeRepository, RegionActivationRepository regionActivationRepository, ServiceActivationRepository serviceActivationRepository, SystemRequirementRepository systemRequirementRepository) {
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.genreRepository = genreRepository;
        this.authorizationTokenRepository = authorizationTokenRepository;
        this.basketRepository = basketRepository;
        this.publisherRepository = publisherRepository;
        this.developerRepository = developerRepository;
        this.purchaseRepository = purchaseRepository;
        this.reviewRepository = reviewRepository;
        this.roleRepository = roleRepository;
        this.itemTypeRepository = itemTypeRepository;
        this.regionActivationRepository = regionActivationRepository;
        this.serviceActivationRepository = serviceActivationRepository;
        this.systemRequirementRepository = systemRequirementRepository;
    }

    private User isAuthenticated(String token) throws NullPointerException, NoSuchElementException {
        AuthorizationToken authorizationToken = authorizationTokenRepository.findTokenByValue(token);
        if(authorizationToken.getActive() && authorizationToken.getUser().getActive()) {
            return authorizationToken.getUser();
        } else {
            throw new NullPointerException();
        }
    }

    /**
     * Метод определяющий является ли пользователь сессии администратором.
     */
    private boolean isAdmin(String token) throws NullPointerException, NoSuchElementException {
        return isAuthenticated(token).getRoles().contains(roleRepository.getById("admin"));
    }

    /**
     * Метод определяющий является ли пользователь сессии модератором.
     */
    private boolean isModerator(String token) throws NullPointerException, NoSuchElementException {
        return isAuthenticated(token).getRoles().contains(roleRepository.getById("moderator"));
    }

    /**
     * Метод для поиска продуктов по заданным фильтрам
     */
    @RequestMapping(value = "/catalog", method = RequestMethod.GET)
    public ResponseEntity<String> findItemByFilter(
            @RequestParam(required = false) String page,
            HttpServletRequest request) {
        try {
            Page<Item> items = itemRepository.findAll(
                    ItemSpecification.filters(request.getParameterMap()),
                    CustomPage.getPage(page, ITEMS_ON_PAGE)
            );
            return new ResponseEntity<>(
                    new JSONObject()
                            .put("items", JSONConverter.toMinimalJsonArray(items))
                            .put("total_pages", items.getTotalPages())
                            .put("total_items", items.getTotalElements())
                            .toString(),
                    HttpStatus.OK);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/systemRequirement/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> findSystemRequirement(@PathVariable String id) {
        try {
            return new ResponseEntity<>(
                    systemRequirementRepository.getById(Integer.parseInt(id)).toJSONObject().toString(),
                    HttpStatus.OK
            );
        } catch (NullPointerException | NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Метод для получения всех существующих жанров.
     */

    @RequestMapping(value = "/genres", method = RequestMethod.GET)
    public ResponseEntity<String> findGenres() {
        try {
            List<Genre> genres = genreRepository.findAll();
            return new ResponseEntity<>(
                    JSONConverter.toJsonArray(genres).put(itemRepository.count()).toString(),
                    HttpStatus.OK
            );
        } catch (NullPointerException | NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    /*
    Метод для добавления продукта в корзину пользователя.
     */

    @RequestMapping(value = "/basket/{itemId}", method = RequestMethod.PUT)
    public ResponseEntity<?> addBasket(@RequestHeader(value = "Authorization") String token,
                                            @PathVariable String itemId) {
        try {
            Basket basket = isAuthenticated(token).getBasket();
            Set<Item> items = basket.getItems();
            items.add(itemRepository.getById(itemId));
            basketRepository.save(basket.setItems(items));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NullPointerException | NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    /*
    Метод для удаления продукта из корзины пользователя.
     */

    @RequestMapping(value = "/basket/{itemId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteBasket(@RequestHeader(value = "Authorization") String token,
                                          @PathVariable String itemId) {
        try {
            Basket basket = isAuthenticated(token).getBasket();
            Set<Item> items = basket.getItems();
            items.remove(itemRepository.getById(itemId));
            basketRepository.save(basket.setItems(items));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NullPointerException | NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

     /*
    Метод для получения информации о корзине пользователя.
     */

    @RequestMapping(value = "/basket", method = RequestMethod.GET)
    public ResponseEntity<String> getBasket(@RequestHeader(value = "Authorization") String token) {
        try {
            return new ResponseEntity<>(
                    isAuthenticated(token).getBasket().toMinimalJSONObject().toString(),
                    HttpStatus.OK);
        } catch (NullPointerException | NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

     /*
    Метод получения только необходимой информации для отображения продукта в корзине пользователя.
     */

    @RequestMapping(value = "/item/{itemId}/basket", method = RequestMethod.GET)
    public ResponseEntity<String> getItemByIdForBasket(@PathVariable String itemId) {
        try {
            return new ResponseEntity<>(
                    itemRepository.getById(itemId).toMinimalJSONObject().toString(),
                    HttpStatus.OK);
        } catch (NullPointerException | NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    /*
    Получение информации о товаре по ID
     */

    @RequestMapping(value = "/item/{itemId}", method = RequestMethod.GET)
    public ResponseEntity<String> getItemById(@PathVariable String itemId) {
        try {
            Item item = itemRepository.getById(itemId);
            Purchase last_purchase = purchaseRepository.findFirstByItemsIsContainingOrderByDatePurchaseAsc(item);
            return new ResponseEntity<>(
                    item.toJSONObject().toString(),
                    HttpStatus.OK);
        } catch (NullPointerException | NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    /*
    Получение всех существующих разработчиков
     */

    @RequestMapping(value = "/developers", method = RequestMethod.GET)
    public ResponseEntity<String> findDevelopers(@RequestParam(required = false) String page) {
        try {
            return new ResponseEntity<>(
                    JSONConverter.toMinimalJsonArray(developerRepository.findAll(CustomPage.getPage(page, ITEMS_ON_PAGE))).put(developerRepository.count()).toString(),
                    HttpStatus.OK);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    /*
    Получение всех существующих издателей
     */

    @RequestMapping(value = "/publishers", method = RequestMethod.GET)
    public ResponseEntity<String> findPublishers(@RequestParam(required = false) String page) {
        try {
            return new ResponseEntity<>(
                    JSONConverter.toMinimalJsonArray(publisherRepository.findAll(CustomPage.getPage(page, ITEMS_ON_PAGE))).put(publisherRepository.count()).toString(),
                    HttpStatus.OK);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    /*
    Получение информации о разработчике по ID
     */

    @RequestMapping(value = "/developer/{developerId}", method = RequestMethod.GET)
    public ResponseEntity<String> findDeveloper(@PathVariable String developerId) {
        try {
            return new ResponseEntity<>(
                    developerRepository.getById(developerId).toJSONObject().toString(),
                    HttpStatus.OK
            );
        } catch (NullPointerException | NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    /*
    Получение информации о издателе по ID
     */

    @RequestMapping(value = "/publisher/{publisherId}", method = RequestMethod.GET)
    public ResponseEntity<String> findPublisher(@PathVariable String publisherId) {
        try {
            return new ResponseEntity<>(
                    publisherRepository.getById(publisherId).toJSONObject().toString(),
                    HttpStatus.OK
            );
        } catch (NullPointerException | NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    private static class ReviewForm {

        @NotBlank
        private String title;

        @NotBlank
        private String text;

        @NotBlank
        private String authorId;
        @Max(5)
        private Integer stars;

        @NotBlank
        private String itemId;
    }

    /*
    Метод для добавления отзывов к товару, принимает форму с заголовком,
    текстом, ID автора, количеством звёзд в оценке и ID товара для которого оставлен отзыв.
    Заносит все данные в БД.
     */

    @RequestMapping(value = "/review", method = RequestMethod.POST)
    public ResponseEntity<String> addReview(@RequestBody ReviewForm reviewForm) {
        try {
            reviewRepository.save(
                    Review.builder()
                            .title(reviewForm.title)
                            .text(reviewForm.text)
                            .author(userRepository.getById(reviewForm.authorId))
                            .stars(reviewForm.stars)
                            .item(itemRepository.getById(reviewForm.itemId))
                            .build());
            return ResponseEntity.ok("Success");
        } catch (NullPointerException | NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /*
        Удаление отзыва если пользователь является его автором, модератором или администратором
     */
    @CrossOrigin
    @RequestMapping(value = "/review/{reviewId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteReview(@RequestHeader(value = "Authorization") String token, @PathVariable String reviewId) {
        try {
            Review review = reviewRepository.getById(Integer.parseInt(reviewId));
            if(review.getAuthor().equals(isAuthenticated(token)) || isModerator(token) || isAdmin(token)) {
                reviewRepository.delete(review);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        } catch (NullPointerException | NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /*
        Получение отзывов по ID товара
     */

    @RequestMapping(value = "/review/{itemId}", method = RequestMethod.GET)
    public ResponseEntity<String> getReviews(@PathVariable String itemId, @RequestParam(required = false) String page) {
        try {
            Page<Review> reviews = reviewRepository.findAllByItem(itemRepository.getById(itemId), CustomPage.getPage(page, ITEMS_ON_PAGE));
            return ResponseEntity.ok(
                    new JSONObject()
                            .put("reviews", JSONConverter.toJsonArray(reviews))
                            .put("total_elements", reviews.getTotalElements())
                            .put("total_pages", reviews.getTotalPages()).toString()
            );
        } catch (NullPointerException | NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /*
    Добавление издателя если пользователь модератор или администратор
     */

    @RequestMapping(value = "/add/publisher", method = RequestMethod.POST)
    public ResponseEntity<String> addPublisher(@RequestHeader(value = "Authorization") String token, @RequestParam("name") String name, @RequestParam("image") MultipartFile image) {
        try {
            if(isAdmin(token) || isModerator(token)) {
                String imageTitle = Cryptography.generatorString(32);
                return ResponseEntity.created(
                        URI.create(
                                "/publisher/" + publisherRepository.save(Publisher.builder()
                                        .id(StringFormatter.toId(name))
                                        .title(name)
                                        .img(ImageController.addImage("publisher", imageTitle, image)).build()).getId()
                        )
                ).build();
            } else {
                throw new NullPointerException();
            }
        } catch (NullPointerException | NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    /*
    Добавление разработчика если пользователь модератор или администратор
     */

    @RequestMapping(value = "/add/developer", method = RequestMethod.POST)
    public ResponseEntity<String> addDeveloper(@RequestHeader(value = "Authorization") String token, @RequestParam("name") String name, @RequestParam("image") MultipartFile image) {
        try {
            if(isAdmin(token) || isModerator(token)) {
                String imageTitle = Cryptography.generatorString(32);
                return ResponseEntity.created(
                        URI.create(
                                "/developer/" + developerRepository.save(Developer.builder()
                                        .id(StringFormatter.toId(name))
                                        .title(name)
                                        .img(ImageController.addImage("developer", imageTitle, image)).build()).getId()
                        )
                ).build();
            } else {
                throw new NullPointerException();
            }
        } catch (NullPointerException | NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    /*
     Получение всех существующих жанров, типов продукта, регионов активации, сервисов для активации
     и системных требований.
     */

    @RequestMapping(value = "/info/category", method = RequestMethod.GET)
    public ResponseEntity<String> getCategory() {
        try {
            return new ResponseEntity<>(
                    new JSONObject()
                            .put("genre", JSONConverter.toJsonArray(genreRepository.findAll()))
                            .put("itemType", JSONConverter.toJsonArray(itemTypeRepository.findAll()))
                            .put("regionActivation", JSONConverter.toJsonArray(regionActivationRepository.findAll()))
                            .put("serviceActivation", JSONConverter.toJsonArray(serviceActivationRepository.findAll()))
                            .put("systemRequirement", JSONConverter.toJsonArray(systemRequirementRepository.findAll()))
                            .toString(),
                    HttpStatus.OK);
        } catch (NullPointerException | NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    private static class AddCategoryForm {
        private String categoryType;
        private String name;
    }

    /*
    Добавление новой позации доступных категорий для товаров.
     */

    @RequestMapping(value = "/add/category", method = RequestMethod.POST)
    public ResponseEntity<String> addCategory(@RequestHeader(value = "Authorization") String token,
                                              @RequestBody AddCategoryForm addCategoryForm) {
        try {
            if(isAdmin(token) || isModerator(token)) {
                switch (addCategoryForm.categoryType) {
                    case "genre":
                        return new ResponseEntity<>(
                                genreRepository.save(Genre.builder().title(addCategoryForm.name).build())
                                        .toJSONObject().toString(),
                                HttpStatus.CREATED);
                    case "regionActivation":
                        return new ResponseEntity<>(
                                regionActivationRepository.save(RegionActivation.builder().title(addCategoryForm.name).build())
                                        .toJSONObject().toString(),
                                HttpStatus.CREATED);
                    case "itemType":
                        return new ResponseEntity<>(
                                itemTypeRepository.save(ItemType.builder().title(addCategoryForm.name).build())
                                        .toJSONObject().toString(),
                                HttpStatus.CREATED);
                    case "serviceActivation":
                        return new ResponseEntity<>(
                                serviceActivationRepository.save(ServiceActivation.builder().title(addCategoryForm.name).build())
                                        .toJSONObject().toString(),
                                HttpStatus.CREATED);
                    case "systemRequirement":
                        return new ResponseEntity<>(
                                systemRequirementRepository.save(SystemRequirement.builder().title(addCategoryForm.name).build())
                                        .toJSONObject().toString(),
                                HttpStatus.CREATED);
                    default:
                        throw new NullPointerException();
                }
            } else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        } catch (NullPointerException | NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /*
    Удаление позации доступных категорий для товаров.
     */

    @RequestMapping(value = "/delete/{categoryType}/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteCategory(@RequestHeader(value = "Authorization") String token,
                                              @PathVariable String categoryType, @PathVariable Integer id) {
        try {
            if(isAdmin(token) || isModerator(token)) {
                switch (categoryType) {
                    case "genre":
                        genreRepository.delete(genreRepository.getById(id));
                        return new ResponseEntity<>(HttpStatus.OK);
                    case "regionActivation":
                        regionActivationRepository.delete(regionActivationRepository.getById(id));
                        return new ResponseEntity<>(HttpStatus.OK);
                    case "itemType":
                        itemTypeRepository.delete(itemTypeRepository.getById(id));
                        return new ResponseEntity<>(HttpStatus.OK);
                    case "serviceActivation":
                        serviceActivationRepository.delete(serviceActivationRepository.getById(id));
                        return new ResponseEntity<>(HttpStatus.OK);
                    case "systemRequirement":
                        systemRequirementRepository.delete(systemRequirementRepository.getById(id));
                        return new ResponseEntity<>(HttpStatus.OK);
                    default:
                        throw new NullPointerException();
                }
            } else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        } catch (NullPointerException | NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
