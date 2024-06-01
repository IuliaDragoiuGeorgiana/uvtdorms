package com.uvtdorms.repository;

import com.uvtdorms.repository.entity.Dorm;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IDormRepository extends JpaRepository<Dorm, UUID> {
    @Transactional
    public Dorm getByDormName(String name);

    @Transactional
    public Optional<Dorm> getByDormId(UUID id);
}
