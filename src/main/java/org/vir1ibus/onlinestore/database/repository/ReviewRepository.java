package org.vir1ibus.onlinestore.database.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.vir1ibus.onlinestore.database.entity.Item;
import org.vir1ibus.onlinestore.database.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    Page<Review> findAllByItem(Item item, Pageable page);
}
