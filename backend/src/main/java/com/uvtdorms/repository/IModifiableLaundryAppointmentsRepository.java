package com.uvtdorms.repository;

import java.util.Optional;
import java.util.UUID;

import com.uvtdorms.repository.entity.ModifiableLaundryAppointment;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IModifiableLaundryAppointmentsRepository extends JpaRepository<ModifiableLaundryAppointment, UUID> {
    @SuppressWarnings("null")
    @Transactional
    public Optional<ModifiableLaundryAppointment> findById(UUID id);
}
