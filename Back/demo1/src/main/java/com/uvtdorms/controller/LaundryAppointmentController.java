package com.uvtdorms.controller;

import com.uvtdorms.repository.dto.request.CreateLaundryAppointmentDto;
import com.uvtdorms.services.LaundryAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/laundryappointments")
public class LaundryAppointmentController {

    LaundryAppointmentService laundryAppointmentService;


    @Autowired
    public LaundryAppointmentController(LaundryAppointmentService laundryAppointmentService) {
        this.laundryAppointmentService = laundryAppointmentService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createLaundryAppointment(@RequestBody CreateLaundryAppointmentDto createLaundryAppointmentDto){
        try {
            laundryAppointmentService.createLaundryAppointment(createLaundryAppointmentDto);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}
