package org.vir1ibus.onlinestore.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vir1ibus.onlinestore.database.entity.ItemHasSystemRequirement;
import org.vir1ibus.onlinestore.database.entity.ItemHasSystemRequirementId;

public interface ItemHasSystemRequirementRepository extends JpaRepository<ItemHasSystemRequirement, ItemHasSystemRequirementId> {
}
