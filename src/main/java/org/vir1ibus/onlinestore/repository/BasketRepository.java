package org.vir1ibus.onlinestore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vir1ibus.onlinestore.entity.Basket;

public interface BasketRepository extends JpaRepository<Basket, Integer> {
}
