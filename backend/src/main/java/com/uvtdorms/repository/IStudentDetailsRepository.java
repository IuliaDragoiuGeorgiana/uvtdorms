package com.uvtdorms.repository;

import com.uvtdorms.repository.entity.Dorm;
import com.uvtdorms.repository.entity.StudentDetails;
import com.uvtdorms.repository.entity.User;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IStudentDetailsRepository extends JpaRepository<StudentDetails, UUID> {
    
    @Transactional
    public Optional<StudentDetails> findByUser(User user);

    @Transactional
    public Optional<StudentDetails> findByMatriculationNumber(String matriculationNumber);

    @Transactional
    public List<StudentDetails> findByRoomDorm(Dorm dorm);
    
    @Transactional
    public Optional<StudentDetails> findByUserEmail(String email);
}
