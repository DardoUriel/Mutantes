package com.example.repositories;

import com.example.entities.Mutant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MutantRepository extends JpaRepository<Mutant, UUID> {
    long countByIsMutant(boolean isMutant);
}
