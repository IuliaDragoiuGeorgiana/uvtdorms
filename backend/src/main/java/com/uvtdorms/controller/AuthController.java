package com.uvtdorms.controller;

import com.uvtdorms.repository.dto.UserDto;
import com.uvtdorms.repository.dto.request.CredentialsDto;
import com.uvtdorms.services.UserService;
import com.uvtdorms.utils.UserAuthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController
{
    private final UserService userService;
    private final UserAuthProvider userAuthProvider;

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody CredentialsDto credentialsDto){
        UserDto userDto = userService.login(credentialsDto);
        userDto.setToken(userAuthProvider.createToken(userDto));
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/login-with-token")
    public ResponseEntity<UserDto> loginWithToken(Authentication authentication){
        UserDto user = (UserDto) authentication.getPrincipal();
        user = userService.verifyUserDto(user);
        return ResponseEntity.ok(user);
    }
}
