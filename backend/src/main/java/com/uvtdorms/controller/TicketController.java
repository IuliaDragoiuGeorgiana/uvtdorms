package com.uvtdorms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uvtdorms.repository.dto.TokenDto;
import com.uvtdorms.repository.dto.request.CreateTicketDto;
import com.uvtdorms.services.TicketService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    @PostMapping("/create")
    public ResponseEntity<Void> createTicket(@RequestBody CreateTicketDto createTicketDto,
            Authentication authentication) {
        String email = ((TokenDto) authentication.getPrincipal()).getEmail();
        ticketService.createTicket(email, createTicketDto);
        return ResponseEntity.ok().build();
    }
}
