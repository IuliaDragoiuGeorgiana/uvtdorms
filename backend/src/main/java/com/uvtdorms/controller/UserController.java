package com.uvtdorms.controller;

import com.uvtdorms.repository.dto.request.ChangePasswordDto;
import com.uvtdorms.repository.dto.request.ChangeProfilePictureDto;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uvtdorms.repository.dto.TokenDto;
import com.uvtdorms.repository.dto.request.UpdatePhoneNumberDto;
import com.uvtdorms.repository.dto.response.DisplayInactiveStudentDetails;
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

    @GetMapping("/get-inactive-student-details")
    public ResponseEntity<DisplayInactiveStudentDetails> getInactiveStudentDetails(Authentication authentication) {
        TokenDto userToken = (TokenDto) authentication.getPrincipal();
        DisplayInactiveStudentDetails displayInactiveStudentDetails = userService.getInactiveStudentDetails(userToken.getEmail());
        return ResponseEntity.ok(displayInactiveStudentDetails);
    }

    @PostMapping("/update-phone-number")
    public ResponseEntity<Void> updatePhoneNumber(Authentication authentication,
            @RequestBody UpdatePhoneNumberDto phoneNumber) {
        TokenDto userToken = (TokenDto) authentication.getPrincipal();
        userService.updatePhoneNumber(phoneNumber, userToken.getEmail());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/update-password")
    public ResponseEntity<Void> updatePassword(Authentication authentication, @RequestBody ChangePasswordDto password) {
        TokenDto userToken = (TokenDto) authentication.getPrincipal();
        System.out.println("Password: " + password.newPassword());
        userService.updatePassword(password, userToken.getEmail());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/update-profile-picture")
    public ResponseEntity<Void> changeProfilePicture(Authentication authentication, @RequestBody ChangeProfilePictureDto profilePicture){
        TokenDto userToken = (TokenDto) authentication.getPrincipal();
        userService.changeProfilePicture(profilePicture, userToken.getEmail());
        return ResponseEntity.ok().build();
    }
}
