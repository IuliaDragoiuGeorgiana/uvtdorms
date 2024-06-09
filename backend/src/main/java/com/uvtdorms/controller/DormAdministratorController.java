package com.uvtdorms.controller;

import com.uvtdorms.repository.dto.TokenDto;
import com.uvtdorms.repository.dto.request.AddNewDormAdministratorDto;
import com.uvtdorms.repository.dto.request.UpdateDormAdministratorDto;
import com.uvtdorms.repository.dto.response.DetailedDormAdministratorDto;
import com.uvtdorms.repository.dto.response.DisplayDormAdministratorDetailsDto;
import com.uvtdorms.repository.dto.response.DormAdministratorDto;
import com.uvtdorms.repository.dto.response.DormIdDto;
import com.uvtdorms.repository.dto.response.EmailDto;
import com.uvtdorms.repository.dto.response.StatisticsCountDto;
import com.uvtdorms.services.DormAdministratorService;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dorm-administrator-details")
@RequiredArgsConstructor
public class DormAdministratorController {
    private final DormAdministratorService dormAdministratorService;

    @GetMapping("/get-administrator-details")
    public ResponseEntity<DisplayDormAdministratorDetailsDto> getDormAdministratorDetails(
            Authentication authentication) {
        TokenDto token = (TokenDto) authentication.getPrincipal();
        DisplayDormAdministratorDetailsDto displayDormAdministratorDetailsDto = dormAdministratorService
                .getDormAdministratorDetails(token.getEmail());

        return ResponseEntity.ok(displayDormAdministratorDetailsDto);
    }

    @GetMapping("/get-administrated-dorm-id")
    public ResponseEntity<DormIdDto> getAdministratedDormId(Authentication authentication) {
        TokenDto token = (TokenDto) authentication.getPrincipal();

        return ResponseEntity.ok(dormAdministratorService.getAdministratedDormId(token.getEmail()));
    }

    @GetMapping("/get-available-dorm-administrators")
    public ResponseEntity<List<DormAdministratorDto>> getAvailableDormAdministrators() {
        return ResponseEntity.ok(dormAdministratorService.getAvailableDormAdministrators());
    }

    @GetMapping("/get-all-dorm-administrators")
    public ResponseEntity<List<DetailedDormAdministratorDto>> getAllDormAdministrators() {
        return ResponseEntity.ok(dormAdministratorService.getDormAdministrators());
    }

    @PostMapping("/add-new-dorm-administrator")
    public ResponseEntity<Void> addNewDormAdministrator(
            @RequestBody AddNewDormAdministratorDto addNewDormAdministratorDto) {
        dormAdministratorService.addNewDormAdministrator(addNewDormAdministratorDto);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/update-dorm-administrator-associated-dorm")
    public ResponseEntity<Void> updateDormAdministratorAssociatedDorm(
            @RequestBody UpdateDormAdministratorDto updateDormAdministratorDto) {
        dormAdministratorService.updateAssociatedDorm(updateDormAdministratorDto);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/delete-dorm-administrator")
    public ResponseEntity<Void> deleteDormAdministrator(@RequestBody EmailDto email) {
        dormAdministratorService.deleteDormAdministrator(email.getEmail());

        return ResponseEntity.ok().build();
    }


    @GetMapping("/get-number-of-dorm-administrators")
    public ResponseEntity<StatisticsCountDto> getDormAdministratorCount() {
        return ResponseEntity.ok(dormAdministratorService.getDormAdministratorCount());
    }
}
