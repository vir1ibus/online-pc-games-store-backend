package org.vir1ibus.onlinestore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vir1ibus.onlinestore.entity.ActivateKey;
import org.vir1ibus.onlinestore.entity.Item;

import java.util.List;

public interface ActivateKeyRepository extends JpaRepository<ActivateKey, Integer> {
    Integer countByItemAndPurchaseIsNull(Item item);

    ActivateKey findFirstByItemAndPurchaseIsNull(Item item);
}
