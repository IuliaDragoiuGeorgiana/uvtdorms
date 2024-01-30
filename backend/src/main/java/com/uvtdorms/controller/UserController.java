package com.uvtdorms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uvtdorms.repository.dto.response.EmailDto;
import com.uvtdorms.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    UserController(UserService userService)
    {
        this.userService = userService;
    }

    @GetMapping("/get-test-user")
    public ResponseEntity<?> getTestUser()
    {
        try {
            EmailDto user = userService.getTestUser();
            return ResponseEntity.ok(user);
        }
        catch(Exception e)
        {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
}
