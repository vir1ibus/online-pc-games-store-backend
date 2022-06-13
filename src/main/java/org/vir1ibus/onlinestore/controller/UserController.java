package org.vir1ibus.onlinestore.controller;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vir1ibus.onlinestore.entity.User;
import org.vir1ibus.onlinestore.repository.ItemRepository;
import org.vir1ibus.onlinestore.repository.UserRepository;
import org.vir1ibus.onlinestore.specification.UserSpecification;
import org.vir1ibus.onlinestore.utils.CustomPage;
import org.vir1ibus.onlinestore.utils.JSONConverter;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/")
public class UserController {

    UserRepository userRepository;
    ItemRepository itemRepository;

    public UserController(UserRepository userRepository, ItemRepository itemRepository) {
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
    }
}
