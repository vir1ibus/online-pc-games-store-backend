package org.vir1ibus.onlinestore.controller;

import org.springframework.web.bind.annotation.*;
import org.vir1ibus.onlinestore.database.repository.ItemRepository;
import org.vir1ibus.onlinestore.database.repository.UserRepository;

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
