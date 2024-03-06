package com.uvtdorms.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;

import com.uvtdorms.repository.dto.TokenDto;
import com.uvtdorms.repository.dto.response.RegisterRequestDto;
import com.uvtdorms.repository.entity.Dorm;
import com.uvtdorms.services.DormAdministratorService;
import com.uvtdorms.services.RegisterRequestService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/register-requests")
public class RegisterRequestController {
    private final RegisterRequestService registerRequestService;
    private final DormAdministratorService dormAdministratorService;

    @GetMapping("/get-register-requests-from-dorm")
    public ResponseEntity<List<RegisterRequestDto>> getRegisterRequestsFromDorm(Authentication authentication) {
        TokenDto token = (TokenDto) authentication.getPrincipal();

        Dorm dorm = dormAdministratorService.getAdministratorDormByEmail(token.getEmail());

        return ResponseEntity.ok(registerRequestService.getRegisterRequestsFromDorm(dorm));
    }

    @PostMapping("/accept-register-request")
    public ResponseEntity<Void> acceptRegisterRequest(@RequestBody RegisterRequestDto registerRequestDto)
    {
        registerRequestService.acceptRegisterRequest(registerRequestDto);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/decline-register-request")
    public ResponseEntity<Void> declineRegisterRequest(@RequestBody RegisterRequestDto registerRequestDto)
    {
        registerRequestService.declineRegisterRequest(registerRequestDto);

        return ResponseEntity.ok().build();
    }
}
