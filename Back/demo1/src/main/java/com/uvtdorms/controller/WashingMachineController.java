package com.uvtdorms.controller;

import com.uvtdorms.repository.entity.WashingMachine;
//import com.uvtdorms.services.WashingMachineService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//@RestController
//@RequestMapping("/api/washingmachines")
//public class WashingMachineController {
//    private final WashingMachineService washingMachineService;
//
//
//    public WashingMachineController(WashingMachineService washingMachineService) {
//        this.washingMachineService = washingMachineService;
//    }
//
//    @PostMapping
//    public ResponseEntity<WashingMachine> addWashingMachine(@RequestBody WashingMachine washingMachine) {
//        WashingMachine newWashingMachine = washingMachineService.addWashingMachine(washingMachine);
//        return new ResponseEntity<>(newWashingMachine, HttpStatus.CREATED);
//    }
//
//    @DeleteMapping("/{machineId}")
//    public ResponseEntity<Void> deleteWashingMachine(@PathVariable Long machineId) {
//        washingMachineService.deleteWashingMachine(machineId);
//        return ResponseEntity.ok().build();
//    }
//
//    @GetMapping("/{machineId}")
//    public ResponseEntity<WashingMachine> getWashingMachineById(@PathVariable Long machineId) {
//        WashingMachine washingMachine = washingMachineService.getWashingMachineById(machineId);
//        if (washingMachine.getMachineId() == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        return ResponseEntity.ok(washingMachine);
//    }
//
//    @GetMapping
//    public List<WashingMachine> getAllWashingMachines() {
//        return washingMachineService.getAllWashingMachines();
//    }
//
//}
