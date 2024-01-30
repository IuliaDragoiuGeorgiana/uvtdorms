package com.uvtdorms.controller;

import com.uvtdorms.repository.dto.UserDto;
import com.uvtdorms.repository.dto.request.CredentialsDto;
import com.uvtdorms.repository.dto.response.EmailDto;
import com.uvtdorms.services.UserService;
import com.uvtdorms.utils.UserAuthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
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
}
