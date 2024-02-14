package com.uvtdorms.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uvtdorms.repository.entity.Dorm;
import com.uvtdorms.repository.entity.RegisterRequest;

public interface IRegisterRequestRepository extends JpaRepository<RegisterRequest, UUID> {
    List<RegisterRequest> findByRoomDorm(Dorm dorm);
}
