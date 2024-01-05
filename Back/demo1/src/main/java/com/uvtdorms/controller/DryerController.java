package com.uvtdorms.controller;

import com.uvtdorms.repository.dto.response.DryerDto;
import com.uvtdorms.repository.dto.response.WashingMachineDto;
import com.uvtdorms.services.DryerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dryers")

public class DryerController {
    private final DryerService dryerService;

    public DryerController(DryerService dryerService) {
        this.dryerService = dryerService;
    }

    @GetMapping("/get-dryer-from-dorm/{dormId}")
    public ResponseEntity<?> getDyerFromDorm(@PathVariable("dormId") String dormId){

        try {
            List<DryerDto> dryersDtos =  this.dryerService.getDryerFromDorm(dormId);
            return ResponseEntity.ok(dryersDtos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}
