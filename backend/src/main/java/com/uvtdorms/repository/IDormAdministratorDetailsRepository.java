package com.uvtdorms.repository;

import com.uvtdorms.repository.entity.DormAdministratorDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IDormAdministratorDetailsRepository extends JpaRepository<DormAdministratorDetails, UUID> {
}
