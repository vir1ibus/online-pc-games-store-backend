package org.vir1ibus.onlinestore.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.vir1ibus.onlinestore.database.entity.ActivateKey;
import org.vir1ibus.onlinestore.database.entity.Purchase;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Set;

@Component
public record EmailService(JavaMailSender emailSender) {

    /*
    Метод для отправки ссылки подтверждения почты пользователя на электронную почту
     */
    public void sendConfirmationMessage(String to, String text_message) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@vir1ibus-shop.com");
        message.setTo(to);
        message.setSubject("Подтверждение аккаунта.");
        message.setText(text_message);
        emailSender.send(message);
    }

    /*
    Метод для отправки купленного товара пользователем на электронную почту
     */
    public void sendActivateKeys(String to, Purchase purchase, Set<ActivateKey> activateKeys) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("noreply@vir1ibus-shop.com");
            helper.setTo(to);
            message.setSubject("Заказ №" + purchase.getId() + " в vir1ibus shop.");
            StringBuilder activateKeysMessage = new StringBuilder();
            for(ActivateKey key : activateKeys) {
                activateKeysMessage
                        .append("<p style=\"font-size:1.25em;\">Игра <b style=\"color:rgb(234, 57, 184);\">")
                        .append(key.getItem().getTitle())
                        .append("</b> за ")
                        .append(key.getItem().getPrice() - ((key.getItem().getPrice() / 100) * key.getItem().getDiscount()))
                        .append(" рублей - ключ активации: ")
                        .append(key.getValue())
                        .append("</p>");
            }
            helper.setText("<html>" +
                    "<head>" +
                    "<link href='https://fonts.googleapis.com/css?family=Balsamiq Sans' rel='stylesheet'>" +
                    "</head>" +
                    "<body style=\"background-image: linear-gradient(#17082e 0%, #1a0933 7%, #1a0933 80%, #0c1f4c 100%); color: #fff; font-family: 'Balsamiq Sans'; background-repeat: no-repeat; height: 100% !important; padding: 3em;\">" +
                    "<h3>Вы совершили покупку в магазине <span style=\"color:rgb(234, 57, 184);\">vir1ibus shop</span> в <span style=\"color:rgb(234, 57, 184);\">13:30 27.05.2022</span> на сумму 200 рублей.</h3>" +
                    "<p style=\"font-size:1.25em;\">Ваши товары:</p>" +
                    activateKeysMessage +
                    "<p style=\"font-size:1.25em;\">Приятной игры!</p>" +
                    "</body>" +
                    "</html>", true);
            emailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
