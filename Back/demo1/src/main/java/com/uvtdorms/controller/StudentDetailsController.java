package com.uvtdorms.controller;

import com.uvtdorms.repository.dto.response.DormIdDto;
import com.uvtdorms.services.StudentDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/studentdetails")
public class StudentDetailsController {

    StudentDetailsService studentDetailsService;


    public StudentDetailsController(StudentDetailsService studentDetailsService) {
        this.studentDetailsService = studentDetailsService;
    }

    @GetMapping("/get-student-dorm-id/{studentEmail}")
    public ResponseEntity<?> getStudentDormId(@PathVariable ("studentEmail") String studentEmail) {
        try {

            DormIdDto dormIdDto= studentDetailsService.getStudentDormId(studentEmail);
            return ResponseEntity.ok(dormIdDto);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }

    }
}
