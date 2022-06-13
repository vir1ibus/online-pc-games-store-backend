package org.vir1ibus.onlinestore.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Objects;

public class CustomPage {

    public static Pageable getPage(String page, Integer itemsOnPage) {
        if (page == null) {
            return PageRequest.of(0, itemsOnPage);
        } else {
            return PageRequest.of(Integer.parseInt(page), itemsOnPage);
        }
    }

    public static Pageable getPage(Integer page, Integer itemsOnPage) {
        return PageRequest.of(Objects.requireNonNullElse(page, 0), itemsOnPage);
    }
}
