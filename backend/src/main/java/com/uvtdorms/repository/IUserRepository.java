package com.uvtdorms.repository;

import com.uvtdorms.repository.entity.User;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IUserRepository extends JpaRepository<User, UUID> {
    @Transactional
    public Optional<User> getByEmail(String email);

    @Transactional
    public Optional<User> findByPhoneNumber(String phoneNumber);
}
