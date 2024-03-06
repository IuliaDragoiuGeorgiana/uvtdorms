package com.uvtdorms.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uvtdorms.repository.entity.Dorm;
import com.uvtdorms.repository.entity.RegisterRequest;
import com.uvtdorms.repository.entity.Room;
import com.uvtdorms.repository.entity.StudentDetails;

public interface IRegisterRequestRepository extends JpaRepository<RegisterRequest, UUID> {
    List<RegisterRequest> findByRoomDorm(Dorm dorm);

    Optional<RegisterRequest> findByStudentAndRoom(StudentDetails studentDetails, Room room);
}
