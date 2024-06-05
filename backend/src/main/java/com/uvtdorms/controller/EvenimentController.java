package com.uvtdorms.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uvtdorms.repository.dto.TokenDto;
import com.uvtdorms.repository.dto.request.CreateEvenimentDto;
import com.uvtdorms.repository.dto.request.IdDto;
import com.uvtdorms.repository.dto.request.UpdateEvenimentDto;
import com.uvtdorms.repository.dto.response.EvenimentDetailsDto;
import com.uvtdorms.services.EvenimentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/eveniment")
@RequiredArgsConstructor
public class EvenimentController {
    private final EvenimentService evenimentService;

    @GetMapping("/get-eveniment-from-dorm")
    public ResponseEntity<List<EvenimentDetailsDto>> getEvenimentFromDorm(Authentication authentication) {
        TokenDto token = (TokenDto) authentication.getPrincipal();
        return ResponseEntity.ok(evenimentService.getEvenimentsFromDorm(token.getEmail()));
    }

    @PostMapping("/create-eveniment")
    public ResponseEntity<Void> createEveniment(Authentication authentication,
            @RequestBody CreateEvenimentDto createEvenimentDto) {
        TokenDto token = (TokenDto) authentication.getPrincipal();
        evenimentService.createEveniment(token.getEmail(), createEvenimentDto);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/update-eveniment")
    public ResponseEntity<Void> upateEveniment(Authentication authentication,
            @RequestBody UpdateEvenimentDto updateEvenimentDto) {
        TokenDto token = (TokenDto) authentication.getPrincipal();
        evenimentService.updateEveniment(token.getEmail(), updateEvenimentDto);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/delete-eveniment")
    public ResponseEntity<Void> deleteEveniment(@RequestBody IdDto idDto) {
        evenimentService.deleteEveniment(idDto);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/attend-to-eveniment")
    public ResponseEntity<Void> attendToEveniment(Authentication authentication, @RequestBody IdDto idDto) {
        TokenDto token = (TokenDto) authentication.getPrincipal();
        evenimentService.attendToEveniment(token.getEmail(), idDto);

        return ResponseEntity.ok().build();
    }
}
