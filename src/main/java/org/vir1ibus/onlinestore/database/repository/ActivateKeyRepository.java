package org.vir1ibus.onlinestore.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vir1ibus.onlinestore.database.entity.ActivateKey;
import org.vir1ibus.onlinestore.database.entity.Item;

public interface ActivateKeyRepository extends JpaRepository<ActivateKey, Integer> {
    Integer countByItemAndPurchaseIsNull(Item item);

    ActivateKey findFirstByItemAndPurchaseIsNull(Item item);
}
