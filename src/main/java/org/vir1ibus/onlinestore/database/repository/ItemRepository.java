package org.vir1ibus.onlinestore.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.vir1ibus.onlinestore.database.entity.Item;

public interface ItemRepository extends JpaRepository<Item, String>, JpaSpecificationExecutor<Item> {}
