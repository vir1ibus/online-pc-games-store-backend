package org.vir1ibus.onlinestore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.vir1ibus.onlinestore.entity.Item;
import org.vir1ibus.onlinestore.entity.Review;

import java.util.Set;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    Page<Review> findAllByItem(Item item, Pageable page);
}
