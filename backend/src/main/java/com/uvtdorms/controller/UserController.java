package com.uvtdorms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uvtdorms.repository.dto.TokenDto;
import com.uvtdorms.repository.dto.response.UserDetailsDto;
import com.uvtdorms.services.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/get-user-details")
    public ResponseEntity<UserDetailsDto> getUserDetails(Authentication authentication) {
        System.out.println("\n\nIn controller\n\n");
        TokenDto userToken = (TokenDto) authentication.getPrincipal();
        UserDetailsDto userDetailsDto = userService.getUserDetails(userToken.getEmail());

        System.out.println(userDetailsDto.toString());

        return ResponseEntity.ok(userDetailsDto);
    }
}
