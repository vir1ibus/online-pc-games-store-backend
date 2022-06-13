package org.vir1ibus.onlinestore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.vir1ibus.onlinestore.entity.Item;
import org.vir1ibus.onlinestore.entity.User;

import javax.validation.constraints.NotNull;

public interface ItemRepository extends JpaRepository<Item, String>, JpaSpecificationExecutor<Item> {}
