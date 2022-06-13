package org.vir1ibus.onlinestore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vir1ibus.onlinestore.entity.Genre;

public interface GenreRepository extends JpaRepository<Genre, Integer> {}
