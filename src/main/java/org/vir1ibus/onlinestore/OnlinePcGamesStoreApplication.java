package org.vir1ibus.onlinestore;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OnlinePcGamesStoreApplication {
    public static void main(String[] args) {
        try {
            SpringApplication.run(OnlinePcGamesStoreApplication.class, args);
        } catch (BeanCreationException e) {
            if (e.getBeanName().equals("entityManagerFactory")) {
                System.out.println("Init SQL error.");
            }
        }
    }
}
