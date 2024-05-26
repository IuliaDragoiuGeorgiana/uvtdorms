package com.uvtdorms.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uvtdorms.repository.dto.TokenDto;
import com.uvtdorms.repository.dto.request.CreateTicketDto;
import com.uvtdorms.repository.dto.response.TicketDto;
import com.uvtdorms.services.TicketService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

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

    @GetMapping("/get-tickets-from-dorm")
    public ResponseEntity<List<TicketDto>> getTicketsFromDorm(Authentication authentication) {
        String email = ((TokenDto) authentication.getPrincipal()).getEmail();
        return ResponseEntity.ok(ticketService.getTicketsFromDorm(email));
    }
}
