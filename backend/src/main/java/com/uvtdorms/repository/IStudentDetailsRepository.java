package com.uvtdorms.repository;

import com.uvtdorms.repository.entity.StudentDetails;
import com.uvtdorms.repository.entity.User;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IStudentDetailsRepository extends JpaRepository<StudentDetails, UUID> {
    Optional<StudentDetails> findByUser(User user);
}
