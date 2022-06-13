package org.vir1ibus.onlinestore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vir1ibus.onlinestore.entity.Developer;

public interface DeveloperRepository extends JpaRepository<Developer, String> {
}

