package org.vir1ibus.onlinestore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vir1ibus.onlinestore.entity.Item;
import org.vir1ibus.onlinestore.entity.RegionActivation;

public interface RegionActivationRepository extends JpaRepository<RegionActivation, Integer> {
}
