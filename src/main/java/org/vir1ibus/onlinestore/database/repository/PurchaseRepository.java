package org.vir1ibus.onlinestore.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vir1ibus.onlinestore.database.entity.Item;
import org.vir1ibus.onlinestore.database.entity.Purchase;
import org.vir1ibus.onlinestore.database.entity.User;

public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {
    Purchase findFirstByItemsIsContainingOrderByDatePurchaseAsc(Item item);

    Purchase getPurchaseByBillIdAndBuyer(String billId, User buyer);
}
