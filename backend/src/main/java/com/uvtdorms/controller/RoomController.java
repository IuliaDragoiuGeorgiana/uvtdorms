package com.uvtdorms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uvtdorms.repository.dto.response.RoomsNumbersDto;
import com.uvtdorms.services.RoomService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @GetMapping("/get-rooms-numbers-from-dorm/{dormName}")
    public ResponseEntity<RoomsNumbersDto> getRoomsNumbersFromDorm(@PathVariable("dormName") String dormName) {
        return ResponseEntity.ok(roomService.getRoomsNumbersFromDorm(dormName));
    }
}
