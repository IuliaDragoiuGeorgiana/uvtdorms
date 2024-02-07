package com.uvtdorms.controller;

import com.uvtdorms.repository.dto.response.DryerDto;
import com.uvtdorms.services.DryerService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dryers")
@RequiredArgsConstructor
public class DryerController {
    private final DryerService dryerService;

    @GetMapping("/get-dryer-from-dorm/{dormId}")
    public ResponseEntity<List<DryerDto>> getDyerFromDorm(@PathVariable("dormId") String dormId) {
        return ResponseEntity.ok(this.dryerService.getDryerFromDorm(dormId));
    }
}
