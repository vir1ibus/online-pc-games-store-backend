package org.vir1ibus.onlinestore.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vir1ibus.onlinestore.database.entity.BlockList;
import org.vir1ibus.onlinestore.database.entity.User;

import java.util.List;

public interface BlockListRepository extends JpaRepository<BlockList, Integer> {
    List<BlockList> findAllByUser(User user);
}
