package com.mowitnow.repositories;

import com.mowitnow.entities.Grille;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrilleRepository extends JpaRepository<Grille, Long> {
}
