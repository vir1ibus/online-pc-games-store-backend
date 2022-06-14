package org.vir1ibus.onlinestore.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vir1ibus.onlinestore.database.entity.Genre;
import org.vir1ibus.onlinestore.database.entity.Screenshot;

public interface ScreenshotRepository extends JpaRepository<Screenshot, Integer> {
}
