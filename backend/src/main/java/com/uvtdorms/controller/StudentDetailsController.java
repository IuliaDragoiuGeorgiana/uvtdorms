package com.uvtdorms.controller;

import com.uvtdorms.repository.dto.TokenDto;
import com.uvtdorms.repository.dto.response.DormIdDto;
import com.uvtdorms.services.StudentDetailsService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/studentdetails")
@RequiredArgsConstructor
public class StudentDetailsController {
    private final StudentDetailsService studentDetailsService;

    @GetMapping("/get-student-dorm-id")
    public ResponseEntity<DormIdDto> getStudentDormId(Authentication authentication) {
        TokenDto user = (TokenDto) authentication.getPrincipal();
        DormIdDto dormId = studentDetailsService.getStudentDormId(user.getEmail());

        return ResponseEntity.ok(dormId);
    }
}
