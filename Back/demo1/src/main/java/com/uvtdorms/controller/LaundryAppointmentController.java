package com.uvtdorms.controller;

import com.uvtdorms.repository.dto.request.CreateLaundryAppointmentDto;
import com.uvtdorms.repository.dto.request.FreeIntervalDto;
import com.uvtdorms.services.LaundryAppointmentService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        System.out.println("In controller\n");
        try {
            laundryAppointmentService.createLaundryAppointment(createLaundryAppointmentDto);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PostMapping("/get-free-intervals")
    public ResponseEntity<?> getFreeIntervalsForCreatingAppointment(@RequestBody FreeIntervalDto freeIntervalDto)
    {
        System.out.println("\n\n\nHello\n\n\n");
        try {
            List<Integer> freeIntervals = laundryAppointmentService.getFreeIntervalsForCreatingAppointment(freeIntervalDto);
            return ResponseEntity.status(HttpStatus.OK).body(freeIntervals);
        }
        catch(Exception e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
}
