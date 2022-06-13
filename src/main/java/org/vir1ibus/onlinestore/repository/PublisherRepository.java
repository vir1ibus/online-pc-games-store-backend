package org.vir1ibus.onlinestore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vir1ibus.onlinestore.entity.Publisher;

public interface PublisherRepository extends JpaRepository<Publisher, String> {
}
