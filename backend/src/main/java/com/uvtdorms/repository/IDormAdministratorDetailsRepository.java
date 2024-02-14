package com.uvtdorms.repository;

import com.uvtdorms.repository.entity.DormAdministratorDetails;
import com.uvtdorms.repository.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IDormAdministratorDetailsRepository extends JpaRepository<DormAdministratorDetails, UUID> {
    Optional<DormAdministratorDetails> findByAdministrator(User user);
}
