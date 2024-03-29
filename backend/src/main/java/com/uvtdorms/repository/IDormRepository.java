package com.uvtdorms.repository;

import com.uvtdorms.repository.entity.Dorm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IDormRepository extends JpaRepository<Dorm, UUID> {
    public Dorm getByDormName(String name);
}
