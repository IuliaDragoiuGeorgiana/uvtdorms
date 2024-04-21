package com.uvtdorms.controller;

import com.uvtdorms.repository.dto.TokenDto;
import com.uvtdorms.repository.dto.request.EditRoomDto;
import com.uvtdorms.repository.dto.response.DisplayStudentDetailsDto;
import com.uvtdorms.repository.dto.response.DormIdDto;
import com.uvtdorms.repository.dto.response.EmailDto;
import com.uvtdorms.repository.dto.response.StudentDetailsDto;
import com.uvtdorms.repository.entity.Dorm;
import com.uvtdorms.services.DormAdministratorService;
import com.uvtdorms.services.StudentDetailsService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/studentdetails")
@RequiredArgsConstructor
public class StudentDetailsController {
    private final StudentDetailsService studentDetailsService;
    private final DormAdministratorService dormAdministratorService;

    @GetMapping("/get-student-dorm-id")
    public ResponseEntity<DormIdDto> getStudentDormId(Authentication authentication) {
        TokenDto user = (TokenDto) authentication.getPrincipal();
        DormIdDto dormId = studentDetailsService.getStudentDormId(user.getEmail());

        return ResponseEntity.ok(dormId);
    }

    @GetMapping("/get-all-students-from-dorm")
    public ResponseEntity<List<StudentDetailsDto>> getAllStudentsFromDorm(Authentication authentication) {
        TokenDto token = (TokenDto) authentication.getPrincipal();
        Dorm dorm = dormAdministratorService.getAdministratorDormByEmail(token.getEmail());

        return ResponseEntity.ok(studentDetailsService.getAllStudentsFromDorm(dorm));
    }

    @PostMapping("/update-room-number")
    public ResponseEntity<Void> updateRoomNumber(@RequestBody EditRoomDto editRoomDto) {
        studentDetailsService.updateRoomNumber(editRoomDto);

        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/delete-student-from-dorm")
    public ResponseEntity<Void> deleteStudentFromDorm(@RequestBody EmailDto emailDto) {
        studentDetailsService.deleteFromDorm(emailDto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get-student-details")
    public ResponseEntity<DisplayStudentDetailsDto> getStudentDetails(Authentication authentication) {
        TokenDto token = (TokenDto) authentication.getPrincipal();
        DisplayStudentDetailsDto studentDetails = studentDetailsService.getStudentDetails(token.getEmail());

        return ResponseEntity.ok(studentDetails);
    }
}
