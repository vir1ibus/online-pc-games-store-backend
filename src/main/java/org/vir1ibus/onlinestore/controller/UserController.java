package org.vir1ibus.onlinestore.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vir1ibus.onlinestore.database.entity.AuthorizationToken;
import org.vir1ibus.onlinestore.database.entity.Role;
import org.vir1ibus.onlinestore.database.entity.User;
import org.vir1ibus.onlinestore.database.repository.AuthorizationTokenRepository;
import org.vir1ibus.onlinestore.database.repository.ItemRepository;
import org.vir1ibus.onlinestore.database.repository.RoleRepository;
import org.vir1ibus.onlinestore.database.repository.UserRepository;
import org.vir1ibus.onlinestore.utils.JSONConverter;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import java.util.*;

@RestController
@RequestMapping("/")
public class UserController {

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final AuthorizationTokenRepository authorizationTokenRepository;
    private final RoleRepository roleRepository;

    public UserController(UserRepository userRepository, ItemRepository itemRepository, AuthorizationTokenRepository authorizationTokenRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.authorizationTokenRepository = authorizationTokenRepository;
        this.roleRepository = roleRepository;
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

    @RequestMapping(value = "/get/users", method = RequestMethod.GET)
    public ResponseEntity<String> getUsers(@RequestHeader("Authorization") String token) {
        try {
            if(isAdmin(token)) {
                return new ResponseEntity<>(
                        JSONConverter.toJsonArray(userRepository.findAll()).toString(),
                        HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        } catch (NullPointerException | NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/get/users/minimal", method = RequestMethod.GET)
    public ResponseEntity<String> getUsersMinimal(@RequestHeader("Authorization") String token) {
        try {
            if(isAdmin(token)) {
                return new ResponseEntity<>(
                        JSONConverter.toMinimalJsonArray(userRepository.findAll()).toString(),
                        HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        } catch (NullPointerException | NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/get/user/{userId}", method = RequestMethod.GET)
    public ResponseEntity<String> getUser(@RequestHeader("Authorization") String token,
                                          @PathVariable String userId) {
        try {
            if(isAdmin(token)) {
                return new ResponseEntity<>(
                        userRepository.findById(userId).get().toJSONObject().toString(),
                        HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        } catch (NullPointerException | NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/get/roles", method = RequestMethod.GET)
    public ResponseEntity<String> getRoles(@RequestHeader("Authorization") String token) {
        try {
            if(isAdmin(token)) {
                return new ResponseEntity<>(
                        JSONConverter.toMinimalJsonArray(roleRepository.findAll()).toString(),
                        HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        } catch (NullPointerException | NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    private static class UserForm {

        @NotBlank
        private String id;

        @NotBlank
        private String email;

        @NotBlank
        private String username;

        private String[] roles;
    }

    @RequestMapping(value = "/save/user", method = RequestMethod.POST)
    public ResponseEntity<String> saveUser(@RequestHeader("Authorization") String token, @RequestBody UserForm userForm) {
        try {
            if(isAdmin(token)) {
                User user = userRepository.findById(userForm.id).get();
                user.setUsername(userForm.username);
                user.setEmail(userForm.email);
                LinkedHashSet<Role> roles = new LinkedHashSet<>();
                for(String role : userForm.roles) {
                    roles.add(roleRepository.findById(role).get());
                }
                user.setRoles(roles);
                return new ResponseEntity<>(
                        userRepository.save(user).toJSONObject().toString(),
                        HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        } catch (NullPointerException | NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
