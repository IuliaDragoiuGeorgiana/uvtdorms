package com.uvtdorms.repository;

import com.uvtdorms.repository.entity.Dorm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IDormRepository extends JpaRepository<Dorm,Long> {
    public Optional<Dorm>getByDormName(String name);
}
