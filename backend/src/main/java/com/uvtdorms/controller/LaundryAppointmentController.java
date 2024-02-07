package com.uvtdorms.controller;

import com.uvtdorms.repository.dto.request.CreateLaundryAppointmentDto;
import com.uvtdorms.repository.dto.request.FreeIntervalDto;
import com.uvtdorms.services.LaundryAppointmentService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/laundryappointments")
@RequiredArgsConstructor
public class LaundryAppointmentController {

    private final LaundryAppointmentService laundryAppointmentService;

    @PostMapping("/create")
    public ResponseEntity<Void> createLaundryAppointment(@RequestBody CreateLaundryAppointmentDto createLaundryAppointmentDto){
        laundryAppointmentService.createLaundryAppointment(createLaundryAppointmentDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/get-free-intervals")
    public ResponseEntity<List<Integer>> getFreeIntervalsForCreatingAppointment(@RequestBody FreeIntervalDto freeIntervalDto)
    {
        return ResponseEntity.ok(laundryAppointmentService.getFreeIntervalsForCreatingAppointment(freeIntervalDto));
    }
}
