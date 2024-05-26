package com.uvtdorms.repository;

import com.uvtdorms.repository.entity.UserRoles;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRolesRepository extends JpaRepository<UserRoles, UUID> {
}
