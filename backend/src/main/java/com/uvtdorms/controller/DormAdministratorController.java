package com.uvtdorms.controller;

import com.uvtdorms.repository.dto.TokenDto;
import com.uvtdorms.repository.dto.response.DisplayDormAdministratorDetailsDto;
import com.uvtdorms.repository.dto.response.DormAdministratorDto;
import com.uvtdorms.repository.dto.response.DormIdDto;
import com.uvtdorms.services.DormAdministratorService;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dorm-administrator-details")
@RequiredArgsConstructor
public class DormAdministratorController {
    private final DormAdministratorService dormAdministratorService;

    @GetMapping("/get-administrator-details")
    public ResponseEntity<DisplayDormAdministratorDetailsDto> getDormAdministratorDetails(
            Authentication authentication) {
        TokenDto token = (TokenDto) authentication.getPrincipal();
        DisplayDormAdministratorDetailsDto displayDormAdministratorDetailsDto = dormAdministratorService
                .getDormAdministratorDetails(token.getEmail());

        return ResponseEntity.ok(displayDormAdministratorDetailsDto);
    }

    @GetMapping("/get-administrated-dorm-id")
    public ResponseEntity<DormIdDto> getAdministratedDormId(Authentication authentication) {
        TokenDto token = (TokenDto) authentication.getPrincipal();

        return ResponseEntity.ok(dormAdministratorService.getAdministratedDormId(token.getEmail()));
    }

    @GetMapping("/get-available-dorm-administrators")
    public ResponseEntity<List<DormAdministratorDto>> getAvailableDormAdministrators() {
        return ResponseEntity.ok(dormAdministratorService.getAvailableDormAdministrators());
    }
}
