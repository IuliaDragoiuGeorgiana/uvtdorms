package com.uvtdorms.controller;

import com.uvtdorms.repository.dto.TokenDto;
import com.uvtdorms.repository.dto.request.CreateLaundryAppointmentDto;
import com.uvtdorms.repository.dto.request.GetFreeIntervalDto;
import com.uvtdorms.repository.dto.request.WashingMachineIdDto;
import com.uvtdorms.repository.dto.response.FreeIntervalsDto;
import com.uvtdorms.repository.dto.response.LaundryAppointmentsDto;
import com.uvtdorms.services.LaundryAppointmentService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    public ResponseEntity<Void> createLaundryAppointment(
            @RequestBody CreateLaundryAppointmentDto createLaundryAppointmentDto, Authentication authentication) {
        TokenDto tokenDto = (TokenDto) authentication.getPrincipal();
        laundryAppointmentService.createLaundryAppointment(createLaundryAppointmentDto, tokenDto.getEmail());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/get-free-intervals")
    public ResponseEntity<FreeIntervalsDto> getFreeIntervalsForCreatingAppointment(
            @RequestBody GetFreeIntervalDto freeIntervalDto) {
        return ResponseEntity.ok(laundryAppointmentService.getFreeIntervalsForCreatingAppointment(freeIntervalDto));
    }

    @PostMapping("/get-weekly-appointments-for-dorm-for-washing-machine")
    public ResponseEntity<List<LaundryAppointmentsDto>> getWeeklyAppointmentsForDormForWashingMachine(
            @RequestBody WashingMachineIdDto getWeeklyAppointmentsForDormForWashingMachineDto,
            Authentication authentication) {
        TokenDto tokenDto = (TokenDto) authentication.getPrincipal();

        return ResponseEntity.ok(laundryAppointmentService.getWeeklyAppointmentsForDormForWashingMachine(
                getWeeklyAppointmentsForDormForWashingMachineDto.washingMachineId(), tokenDto.getEmail()));
    }
}
