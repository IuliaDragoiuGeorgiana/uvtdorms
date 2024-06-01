package com.uvtdorms.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uvtdorms.repository.dto.request.UpdateDormAdministratorDto;
import com.uvtdorms.repository.dto.response.AvailableDormDto;
import com.uvtdorms.repository.dto.response.DormDto;
import com.uvtdorms.repository.dto.response.DormIdDto;
import com.uvtdorms.repository.dto.response.DormsNamesDto;
import com.uvtdorms.services.DormService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dorms")
public class DormController {
    private final DormService dormService;

    @GetMapping("/dorms-names")
    public ResponseEntity<DormsNamesDto> getDormsNames() {
        return ResponseEntity.ok(dormService.getDormsNames());
    }

    @GetMapping("/all-dorms")
    public ResponseEntity<List<DormDto>> getAllDorms() {
        return ResponseEntity.ok(dormService.getAllDorms());
    }

    @PostMapping("/add-dorm")
    public ResponseEntity<Void> addDorm(@RequestBody DormDto dormDto) {
        dormService.addDorm(dormDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/update-dorm-administrator")
    public ResponseEntity<Void> updateDormAdministrator(
            @RequestBody UpdateDormAdministratorDto updateDormAdministratorDto) {
        dormService.updateDormAdministrator(updateDormAdministratorDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/delete-dorm")
    public ResponseEntity<Void> deleteDorm(@RequestBody DormIdDto dormId) {
        dormService.deleteDorm(dormId.getId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/available-dorms")
    public ResponseEntity<List<AvailableDormDto>> getAvailableDorms() {
        return ResponseEntity.ok(dormService.getAvailableDorms());
    }
}
