package com.uvtdorms.controller;

import com.uvtdorms.repository.dto.response.WashingMachineDto;
import com.uvtdorms.repository.entity.WashingMachine;
import com.uvtdorms.services.WashingMachineService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/washingmachines")
public class WashingMachineController {
    private final WashingMachineService washingMachineService;


    public WashingMachineController(WashingMachineService washingMachineService) {
        this.washingMachineService = washingMachineService;
    }

    @GetMapping("/get-washing-machines-from-dorm/{dormId}")
    public ResponseEntity<?> getWashingMachinesFromDorm(@PathVariable("dormId") String dormId){

        try {
            List<WashingMachineDto> washingMachineDtos =  this.washingMachineService.getWashingMachinesFromDorm(dormId);
            return ResponseEntity.ok(washingMachineDtos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

}
