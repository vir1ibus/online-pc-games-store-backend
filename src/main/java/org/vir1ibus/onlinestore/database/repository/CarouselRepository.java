package org.vir1ibus.onlinestore.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vir1ibus.onlinestore.database.entity.Carousel;
import org.vir1ibus.onlinestore.database.entity.Item;

public interface CarouselRepository extends JpaRepository<Carousel, Integer> {
}
