package org.vir1ibus.onlinestore.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.vir1ibus.onlinestore.entity.*;
import org.vir1ibus.onlinestore.exception.AuthorizationException;
import org.vir1ibus.onlinestore.repository.*;
import org.vir1ibus.onlinestore.service.EmailService;
import org.vir1ibus.onlinestore.utils.Cryptography;

import org.springframework.validation.ObjectError;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@RestController
@RequestMapping("/authorization")
public class AuthorizationController {

    private final UserRepository userRepository;
    private final AuthorizationTokenRepository authorizationTokenRepository;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final RoleRepository roleRepository;
    private final EmailService emailService;
    private final BasketRepository basketRepository;
    private final LikedRepository likedRepository;

    public AuthorizationController(UserRepository userRepository,
                                   AuthorizationTokenRepository authorizationTokenRepository,
                                   ConfirmationTokenRepository confirmationTokenRepository,
                                   RoleRepository roleRepository, EmailService emailService,
                                   BasketRepository basketRepository, LikedRepository likedRepository) {
        this.userRepository = userRepository;
        this.authorizationTokenRepository = authorizationTokenRepository;
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.roleRepository = roleRepository;
        this.emailService = emailService;
        this.basketRepository = basketRepository;
        this.likedRepository = likedRepository;
    }

    /**
     * Позволяет отправлять в теле ответа удобную для идентификации ошибки на стороне клиента
     **/
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleValidationExceptions(MethodArgumentNotValidException ex) {
        JSONArray errors = new JSONArray();
        List<FieldError> fieldErrors = ex.getFieldErrors();
        List<ObjectError> objectErrors = ex.getAllErrors();
        for(int i = 0; i < fieldErrors.size(); i++){
            errors.put(new JSONObject()
        .put("code", fieldErrors.get(i).getField())
        .put("error", objectErrors.get(i).getDefaultMessage()));
        }
        return errors.toString();
    }

    /**
     * Позволяет определить существует ли сессия с данным токеном
     * получить информацию о пользователе который является авторизоавнным
     **/
    private User isAuthenticated(String token) throws NullPointerException, NoSuchElementException {
        AuthorizationToken authorizationToken = authorizationTokenRepository.findTokenByValue(token);
        if(authorizationToken.getActive() && authorizationToken.getUser().getActive()) {
            return authorizationToken.getUser();
        } else {
            throw new NullPointerException();
        }
    }

    /**
     * Вызывается при REST запросе с URI = '/' и вызывает метод проверки сессии
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<?> isAuthenticatedResponse(@RequestHeader(value = "Authorization") String token) {
        try {
            return new ResponseEntity<>(
                    isAuthenticated(token).toJSONObject().toString(),
                    HttpStatus.OK);
        } catch (NullPointerException | NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    private static class RegistrationForm {
        @Pattern(regexp = "[a-zA-Z0-9._-]{3,75}", message = "The username must be at least 3 characters long and contain only allowed characters (dot, underscore, dash).")
        private String username;

        @Email
        private String email;

        @NotBlank
        private String password;
    }

    /**
     * Метод выполняющий регистрацию пользователя принимающий форму регистрации
     * содержащую в себе имя пользователя, электронную почту и пароль.
     * С помощью класс Cryptography выполняет шифрование пароля по алгоритму SHA-512.
     * Добавляет все данные в базу данных и создаёт ссылку для подтверждения электронной почты
     * и вызывает метод из класса EmailService для отправки этой ссылки на электронную почту
     */

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ResponseEntity<?> registration( @Valid @RequestBody(required = false) RegistrationForm registrationForm,
                                           HttpServletRequest httpServletRequest) {
        try {
            if(userRepository.existsByUsername(registrationForm.username) && userRepository.existsByEmail(registrationForm.email)) {
                return new ResponseEntity<>(
                        new JSONArray()
                                .put(new JSONObject()
                                        .put("code", "username")
                                        .put("error", "Already exists."))
                                .put(new JSONObject()
                                        .put("code", "email")
                                        .put("error", "Already exists."))
                                .toString(),
                        HttpStatus.CONFLICT);
            }
            if(userRepository.existsByUsername(registrationForm.username)) {
                return new ResponseEntity<>(
                        new JSONObject()
                                .put("code", "username")
                                .put("error", "Already exists.")
                                .toString(),
                        HttpStatus.CONFLICT);
            }
            if(userRepository.existsByEmail(registrationForm.email)) {
                return new ResponseEntity<>(
                        new JSONObject()
                                .put("code", "email")
                                .put("error", "Already exists.")
                                .toString(),
                        HttpStatus.CONFLICT);
            }
            String[] encryptPassword = Cryptography.encrypt(registrationForm.password);
            User registered_user = userRepository.save(User.builder()
                            .id(Cryptography.generatorString(10))
                            .username(registrationForm.username)
                            .password(encryptPassword[0])
                            .email(registrationForm.email)
                            .salt(encryptPassword[1])
                            .build());
            Role role = roleRepository.findById("user").get();
            role.getUsers().add(registered_user);
            roleRepository.save(role);
            registered_user.setBasket(basketRepository.save(Basket.builder()
                    .user(registered_user)
                    .build()));
            registered_user.setLiked(likedRepository.save(Liked.builder()
                    .user(registered_user)
                    .build()));
            String confirm_token = Cryptography.generatorString(20);
            confirmationTokenRepository.save(ConfirmationToken.builder()
                    .user(registered_user)
                    .value(confirm_token)
                    .createdAt(LocalDateTime.now())
                    .email(registrationForm.getEmail())
                    .build());
            emailService.sendConfirmationMessage(
                    registrationForm.email,
                    "Перейдите по ссылке " + httpServletRequest.getHeader("referer") + "confirm/" + confirm_token + " для подтверждения аккаунта.");
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (ConstraintViolationException cve) {
            Iterator<ConstraintViolation<?>> constraintViolations = cve.getConstraintViolations().iterator();
            JSONObject jsonObject = new JSONObject();
            while (constraintViolations.hasNext()) {
                ConstraintViolation<?> cv = constraintViolations.next();
                jsonObject.put(cv.getPropertyPath().toString(), cv.getMessage());
            }
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.BAD_REQUEST);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(
                    new JSONObject().put("error", "no request data").toString(),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    private static class ChangeEmailInput {
        private String email;
    }

    /**
     * Метод позволяет пользователю изменить электронную почту аккаунта.
     * Отправляет новую ссылку подтверждения на электронную почту переданную в форму.
     */

    @RequestMapping(value = "/change/email", method = RequestMethod.PUT)
    public ResponseEntity<?> changeEmail(@RequestHeader(name = "Authorization") String token,
                                         @RequestBody ChangeEmailInput changeEmailInput,
                                         HttpServletRequest httpServletRequest) {
        try {
            User user = isAuthenticated(token);
            String confirm_token = Cryptography.generatorString(20);
            confirmationTokenRepository.save(ConfirmationToken.builder()
                    .user(user)
                    .value(confirm_token)
                    .createdAt(LocalDateTime.now())
                    .email(changeEmailInput.email)
                    .build());
            emailService.sendConfirmationMessage(
                    changeEmailInput.email,
                    "Перейдите по ссылке " + httpServletRequest.getHeader("referer") + "confirm/" + confirm_token + " для подтверждения смены электронной почты.");
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NullPointerException | NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    private static class ChangeUsernameInput {
        private String username;
    }

    /**
     * Метод позволяет пользователю изменить имя пользователя аккаунта.
     */

    @RequestMapping(value = "/change/username", method = RequestMethod.POST)
    public ResponseEntity<?> changeUsername(@RequestHeader(name = "Authorization") String token,
                                            @RequestBody ChangeUsernameInput changeUsernameInput,
                                            HttpServletRequest httpServletRequest) {
        try {
            userRepository.save(isAuthenticated(token).setUsername(changeUsernameInput.username));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NullPointerException | NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    private static class ChangePasswordInput {
        private String currentPassword;
        private String newPassword;
    }
    /**
     * Метод позволяет пользователю изменить пароль аккаунта.
     * Получает из формы текущий пароль пользователя, сверяет его с значением в БД.
     * Если значения совпадает, шифрует новый пароль и изменяет его в БД.
     */
    @RequestMapping(value = "/change/password", method = RequestMethod.PUT)
    public ResponseEntity<?> changePassword(@RequestHeader(name = "Authorization") String token,
                                            @RequestBody ChangePasswordInput changePasswordInput) {
        try {
            User user = isAuthenticated(token);
            if(Cryptography.encrypt(changePasswordInput.currentPassword, user.getSalt()).equals(user.getPassword())) {
                userRepository.save(user.setPassword(Cryptography.encrypt(changePasswordInput.newPassword, user.getSalt())));
                List<AuthorizationToken> authorizationTokens = authorizationTokenRepository.findAllByUser(user);
                authorizationTokens.forEach(authorizationToken -> {
                    authorizationToken.setActive(false);
                });
                authorizationTokenRepository.saveAll(authorizationTokens);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                throw new NullPointerException();
            }
        } catch (NullPointerException | NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Метод для проверки подлинности ссылки подтверждения
     * и в случае успеха изменяет данные в БД
     **/

    @RequestMapping(value = "/confirm/{confirmation_token}", method = RequestMethod.PUT)
    public ResponseEntity<?> confirm(@PathVariable String confirmation_token) {
        try {
            ConfirmationToken confirmationToken = confirmationTokenRepository.findTokenByValue(confirmation_token);
            if(!confirmationToken.getUser().getActive() && confirmationToken.getConfirmedAt() == null) {
                userRepository.save(confirmationToken.getUser().setActive(true));
            } else if(confirmationToken.getUser().getActive() && confirmationToken.getConfirmedAt() == null){
                userRepository.save(confirmationToken.getUser().setEmail(confirmationToken.getEmail()));
            } else {
                throw new NullPointerException();
            }
            confirmationTokenRepository.save(confirmationToken.setConfirmedAt(LocalDateTime.now()));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    private static class AuthenticationForm {
        private String username;
        private String password;
    }

    /**
     * Метод принимает в себя форму аутентификации с именем пользователя и паролем,
     * сверяет их с данным в БД. В случае успеха гененрирует токен сессии, заносит его в БД
     * и возвращает в качестве тела ответа.
     */

    @RequestMapping(value = "/authentication", method = RequestMethod.POST)
    public ResponseEntity<?> authentication( @Valid @RequestBody(required = false) AuthenticationForm authenticationForm,
                                             HttpServletRequest httpServletRequest ) {
        try {
            User authUser = userRepository.findByUsername(authenticationForm.username);
            if(authUser == null) {
               throw new AuthorizationException(new JSONObject().put("username", "Username not exists.").toString());
            }
            if(authUser.getPassword().equals(Cryptography.encrypt(authenticationForm.password, authUser.getSalt()))) {
                if(!authUser.getActive()) {
                    throw new AuthorizationException(new JSONObject().put("account", "Account not activated.").toString());
                }
                String token = Cryptography.generatorString(40);
                authorizationTokenRepository.save(AuthorizationToken.builder()
                        .user(authUser)
                        .value(token)
                        .createdAt(LocalDateTime.now())
                        .ip(httpServletRequest.getRemoteAddr())
                        .build());
                return new ResponseEntity<>(
                        authUser.toJSONObject().put("token", token).toString(),
                        HttpStatus.CREATED);
            } else {
                throw new AuthorizationException(new JSONObject().put("password", "Wrong password").toString());
            }
        } catch (NullPointerException e) {
            return new ResponseEntity<>(
                    new JSONObject().put("error", "no request data").toString(),
                    HttpStatus.BAD_REQUEST);
        } catch (AuthorizationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Метод изменяющий состояние токена в БД на не активное. Обнуляет сессию.
     */

    @CrossOrigin
    @RequestMapping(value = "/logout", method = RequestMethod.DELETE)
    public ResponseEntity<?> logout( @RequestHeader(value = "Authorization") String token ) {
        try {
            authorizationTokenRepository.save(authorizationTokenRepository.findTokenByValue(token).setActive(false));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(
                    new JSONObject()
                .put("error", "token not found"),
                    HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Метод аналогичный предыдущему выполняющий действия со всеми токенами,
     * принадлежащие пользователю.
     */

    @CrossOrigin
    @RequestMapping(value = "/logout/all", method = RequestMethod.DELETE)
    public ResponseEntity<?> logoutAll( @RequestHeader(value = "Authorization") String token ) {
        try {
            List<AuthorizationToken> authorizationTokens = authorizationTokenRepository.findAllByUser(
                            authorizationTokenRepository.findTokenByValue(token).getUser());
            authorizationTokens.forEach(elem -> { elem.setActive(false); });
            authorizationTokenRepository.saveAll(authorizationTokens);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(
                    new JSONObject()
            .put("error", "token not found"),
                    HttpStatus.BAD_REQUEST);
        }
    }
}
