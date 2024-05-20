package com.uvtdorms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uvtdorms.repository.dto.request.ResetPasswordDto;
import com.uvtdorms.repository.dto.request.ResetPasswordTokenDto;
import com.uvtdorms.repository.dto.response.EmailDto;
import com.uvtdorms.services.PasswordResetService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/password-reset")
@RequiredArgsConstructor
public class PasswordResetController {
    private final PasswordResetService passwordResetTokenService;

    @PostMapping("/forgot-password")
    public ResponseEntity<Void> forgotPassword(@RequestBody EmailDto emailDto) {
        System.out.println("forgot-password");
        passwordResetTokenService.forgotPassword(emailDto.getEmail());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/validate-token")
    public ResponseEntity<Void> validateToken(@RequestBody ResetPasswordTokenDto resetPasswordTokenDto) {
        passwordResetTokenService.validateToken(resetPasswordTokenDto.token());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(@RequestBody ResetPasswordDto resetPasswordDto) {
        passwordResetTokenService.resetPassword(resetPasswordDto);
        return ResponseEntity.ok().build();
    }
}
