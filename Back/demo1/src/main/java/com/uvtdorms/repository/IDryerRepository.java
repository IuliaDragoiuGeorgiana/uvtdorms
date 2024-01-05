package com.uvtdorms.repository;

import com.uvtdorms.repository.entity.Dorm;
import com.uvtdorms.repository.entity.Dryer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IDryerRepository extends JpaRepository<Dryer, UUID> {

    List<Dryer> findByDorm(Dorm dorm);
}
