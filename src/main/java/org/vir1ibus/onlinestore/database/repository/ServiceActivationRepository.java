package org.vir1ibus.onlinestore.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vir1ibus.onlinestore.database.entity.ServiceActivation;

public interface ServiceActivationRepository extends JpaRepository<ServiceActivation, Integer> {
}
