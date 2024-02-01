package com.uvtdorms.controller;

import com.uvtdorms.repository.dto.TokenDto;
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
    public ResponseEntity<TokenDto> login(@RequestBody CredentialsDto credentialsDto){
        TokenDto tokenDto = userService.login(credentialsDto);
        tokenDto.setToken(userAuthProvider.createToken(tokenDto));
        return ResponseEntity.ok(tokenDto);
    }

    @GetMapping("/login-with-token")
    public ResponseEntity<TokenDto> loginWithToken(Authentication authentication){
        TokenDto token = (TokenDto) authentication.getPrincipal();
        token = userService.verifyToken(token);
        token.setToken(userAuthProvider.createToken(token));
        return ResponseEntity.ok(token);
    }
}
