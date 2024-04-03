package com.uvtdorms.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uvtdorms.repository.entity.Dorm;
import com.uvtdorms.repository.entity.RegisterRequest;
import com.uvtdorms.repository.entity.Room;
import com.uvtdorms.repository.entity.StudentDetails;

import jakarta.transaction.Transactional;

public interface IRegisterRequestRepository extends JpaRepository<RegisterRequest, UUID> {
    
    @Transactional
    List<RegisterRequest> findByRoomDorm(Dorm dorm);
 
    @Transactional
    Optional<RegisterRequest> findByStudentAndRoom(StudentDetails studentDetails, Room room);
    
    @Transactional
    List<RegisterRequest> findByStudent(StudentDetails studentDetails);
}
