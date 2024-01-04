package com.uvtdorms.repository;

import com.uvtdorms.repository.entity.StudentDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IStudentDetailsRepository extends JpaRepository<StudentDetails, Long> {
}
