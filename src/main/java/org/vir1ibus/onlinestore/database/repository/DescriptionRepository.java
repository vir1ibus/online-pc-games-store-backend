package org.vir1ibus.onlinestore.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vir1ibus.onlinestore.database.entity.Description;
import org.vir1ibus.onlinestore.database.entity.Item;

import java.util.List;

public interface DescriptionRepository  extends JpaRepository<Description, Integer> {
    public List<Description> findAllByItem(Item item);
    public Description findByItem(Item item);
}
