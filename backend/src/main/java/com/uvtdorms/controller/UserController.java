package com.uvtdorms.controller;

import org.hibernate.sql.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uvtdorms.repository.dto.TokenDto;
import com.uvtdorms.repository.dto.request.UpdatePhoneNumberDto;
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
        TokenDto userToken = (TokenDto) authentication.getPrincipal();
        UserDetailsDto userDetailsDto = userService.getUserDetails(userToken.getEmail());
        return ResponseEntity.ok(userDetailsDto);
    }

    @PostMapping("/update-phone-number")
    public ResponseEntity<Void> updatePhoneNumber(Authentication authentication, @RequestBody UpdatePhoneNumberDto phoneNumber) {
        TokenDto userToken = (TokenDto) authentication.getPrincipal();
        userService.updatePhoneNumber( phoneNumber, userToken.getEmail());
        return ResponseEntity.ok().build();
    }
}
