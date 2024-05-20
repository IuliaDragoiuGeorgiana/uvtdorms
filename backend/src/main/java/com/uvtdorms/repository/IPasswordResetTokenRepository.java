package com.uvtdorms.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uvtdorms.repository.entity.PasswordResetToken;

import jakarta.transaction.Transactional;

public interface IPasswordResetTokenRepository extends JpaRepository<PasswordResetToken, UUID> {
    @Transactional
    Optional<PasswordResetToken> findByToken(String token);

    @Transactional
    Optional<PasswordResetToken> findByUserEmail(String email);
}
