package com.uvtdorms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uvtdorms.repository.dto.TokenDto;
import com.uvtdorms.repository.dto.request.ModifiableLaundryAppointmentsIdDto;
import com.uvtdorms.services.ModifiableLaundryAppointmentsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/modifiablelaundryappointments")
@RequiredArgsConstructor
public class ModifiableLaundryAppointmentsController {
    private final ModifiableLaundryAppointmentsService modifiableLaundryAppointmentsService;

    @GetMapping("/validate-modifiable-laundry-appointment/{id}")
    public ResponseEntity<Void> validateModifiableLaundryAppointment(@PathVariable String id,
            Authentication authentication) {
        System.out.println("ModifiableLaundryAppointmentsController.validateModifiableLaundryAppointment");
        TokenDto tokenDto = (TokenDto) authentication.getPrincipal();
        modifiableLaundryAppointmentsService.validateModifiableLaundryAppointments(id, tokenDto.getEmail());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/keep-laundry-appointment-without-dryer")
    public ResponseEntity<Void> keepLaundryAppointmentWithoutDryer(
            @RequestBody ModifiableLaundryAppointmentsIdDto modifiableLaundryAppointmentsIdDto) {
        modifiableLaundryAppointmentsService.keepLaundryAppointmentWithoutDryer(modifiableLaundryAppointmentsIdDto);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/cancel-laundry-appointment")
    public ResponseEntity<Void> cancelLaundryAppointment(
            @RequestBody ModifiableLaundryAppointmentsIdDto modifiableLaundryAppointmentsIdDto) {
        modifiableLaundryAppointmentsService.cancelLaundryAppointment(modifiableLaundryAppointmentsIdDto);

        return ResponseEntity.ok().build();
    }
}
