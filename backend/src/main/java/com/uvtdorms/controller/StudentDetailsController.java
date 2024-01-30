package com.uvtdorms.controller;

import com.uvtdorms.repository.dto.UserDto;
import com.uvtdorms.repository.dto.response.DormIdDto;
import com.uvtdorms.services.StudentDetailsService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/studentdetails")
public class StudentDetailsController {

    StudentDetailsService studentDetailsService;


    public StudentDetailsController(StudentDetailsService studentDetailsService) {
        this.studentDetailsService = studentDetailsService;
    }

    @GetMapping("/get-student-dorm-id")
    public ResponseEntity<DormIdDto> getStudentDormId(Authentication authentication) {
        UserDto user = (UserDto) authentication.getPrincipal();
        DormIdDto dormId = studentDetailsService.getStudentDormId(user.getEmail());

        return ResponseEntity.ok(dormId);
    }
}
