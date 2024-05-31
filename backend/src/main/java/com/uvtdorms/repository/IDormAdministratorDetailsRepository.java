package com.uvtdorms.repository;

import com.uvtdorms.repository.entity.Dorm;
import com.uvtdorms.repository.entity.DormAdministratorDetails;
import com.uvtdorms.repository.entity.User;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IDormAdministratorDetailsRepository extends JpaRepository<DormAdministratorDetails, UUID> {
    @Transactional
    Optional<DormAdministratorDetails> findByAdministrator(User user);

    @Transactional
    List<DormAdministratorDetails> findByDorm(Dorm dorm);
}
