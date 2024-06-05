package com.uvtdorms.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.uvtdorms.repository.entity.Dorm;
import com.uvtdorms.repository.entity.Eveniment;

import jakarta.transaction.Transactional;

@Repository
public interface IEvenimentRepository extends JpaRepository<Eveniment, UUID> {
    @Transactional
    List<Eveniment> findByDorm(Dorm dorm);

    @Transactional
    Optional<Eveniment> findById(UUID id);
}
