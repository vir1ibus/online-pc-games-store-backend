package org.vir1ibus.onlinestore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vir1ibus.onlinestore.entity.Role;

public interface RoleRepository extends JpaRepository<Role, String> {
}
