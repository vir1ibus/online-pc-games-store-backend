package org.vir1ibus.onlinestore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vir1ibus.onlinestore.entity.ItemType;

public interface ItemTypeRepository extends JpaRepository<ItemType, Integer> {
}
