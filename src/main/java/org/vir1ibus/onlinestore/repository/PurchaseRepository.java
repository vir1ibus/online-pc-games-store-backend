package org.vir1ibus.onlinestore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vir1ibus.onlinestore.entity.Item;
import org.vir1ibus.onlinestore.entity.Purchase;
import org.vir1ibus.onlinestore.entity.User;

import java.time.LocalDateTime;
import java.util.Set;

public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {
    Purchase findFirstByItemsIsContainingOrderByDatePurchaseAsc(Item item);

    Purchase getPurchaseByBillIdAndBuyer(String billId, User buyer);
}
