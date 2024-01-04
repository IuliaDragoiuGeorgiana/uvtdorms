package com.uvtdorms.repository;

import com.uvtdorms.repository.entity.Dryer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IDryerRepository extends JpaRepository<Dryer, UUID> {
}
