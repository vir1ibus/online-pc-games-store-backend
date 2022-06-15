package org.vir1ibus.onlinestore.test;

import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.vir1ibus.onlinestore.database.repository.AuthorizationTokenRepository;
import org.vir1ibus.onlinestore.database.repository.UserRepository;
import org.vir1ibus.onlinestore.utils.Cryptography;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext
public class AuthorizationControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthorizationTokenRepository authorizationTokenRepository;

    private static String id;
    private static String username = "zaEzRcaUDvQkUQ";
    private static String email = "zaEzRcaUDvQkUQ@zaEzRcaUDvQkUQ.ru";
    private static String password = "zaEzRcaUDvQkUQ1";
    private static String token;

    /**
     * Проверяет корректность регистрации и возвращаемого значения ID пользователя.
     */
    @Test
    @Order(1)
    public void testRegistrationUser() throws Exception {
        id = mvc.perform(post("/authorization/registration/")
                        .content(new JSONObject()
                                .put("username", username)
                                .put("email", email)
                                .put("password", password).toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        userRepository.save(userRepository.findById(id).get().setActive(true));
    }

    /**
     * Проверяет корректность аутентификации после регистрации,
     * а также корректность токена для авторизации.
     */
    @Test
    @Order(2)
    public void testAuthenticationUser() throws Exception {
        token = mvc.perform(post("/authorization/authentication/")
                        .content(new JSONObject()
                                .put("username", username)
                                .put("password", password).toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        token = new JSONObject(token).getString("token");
        Assertions.assertNotNull(authorizationTokenRepository.findTokenByValue(token));
    }

    /**
     * Проверяет работоспосбность функционала смены имени пользователя.
     */
    @Test
    @Order(3)
    public void testChangeUsername() throws Exception {
        username = Cryptography.generatorString(10);
        mvc.perform(post("/authorization/change/username")
                        .header("Authorization", token)
                        .content(new JSONObject()
                                .put("username", username)
                                .toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        Assertions.assertEquals(userRepository.findById(id).get().getUsername(), username);
    }

    /**
     * Проверяет работоспосбность функционала выхода пользователя из аккаунта.
     * (Деактивация токена)
     */
    @Test
    @Order(4)
    public void testLogout() throws Exception {
        mvc.perform(delete("/authorization/logout")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        Assertions.assertNull(authorizationTokenRepository.findTokenByValueAndActiveIsTrue(token));
    }

    /**
     * Проверяет работоспосбность функционала выхода пользователя из аккаунта.
     * (Деактивация всех токенов)
     */
    @Test
    @Order(5)
    public void testLogoutAll() throws Exception {
        testAuthenticationUser();
        mvc.perform(delete("/authorization/logout/all")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        Assertions.assertEquals(authorizationTokenRepository.findAllByUserAndActiveIsTrue(userRepository.findById(id).get()).size(), 0);
    }

    /**
     * Проверяет работоспосбность функционала удаление пользователя из системы.
     */
    @Test
    @Order(6)
    public void testDeleteUser() throws Exception {
        testAuthenticationUser();
        mvc.perform(delete("/authorization/delete")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        Assertions.assertEquals(userRepository.findById(id), Optional.empty());
    }
}
