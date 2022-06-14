package org.vir1ibus.onlinestore.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vir1ibus.onlinestore.database.entity.Publisher;

public interface PublisherRepository extends JpaRepository<Publisher, String> {
}
