package com.uvtdorms.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uvtdorms.repository.dto.TokenDto;
import com.uvtdorms.repository.dto.request.RoomNumberDto;
import com.uvtdorms.repository.dto.response.DetailedRoomDto;
import com.uvtdorms.repository.dto.response.LightUserDto;
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

    @GetMapping("/get-room-details-from-dorm")
    public ResponseEntity<List<DetailedRoomDto>> getRoomsFromDrom(Authentication authentication) {
        TokenDto tokenDto = (TokenDto) authentication.getPrincipal();
        return ResponseEntity.ok(roomService.getRoomsDetailesFromDorm(tokenDto.getEmail()));
    }

    @PostMapping("/add-room-to-dorm")
    public ResponseEntity<Void> addRoomToDorm(Authentication authentication, @RequestBody RoomNumberDto roomNumberDto) {
        TokenDto tokenDto = (TokenDto) authentication.getPrincipal();
        roomService.addNewRoomToDorm(tokenDto.getEmail(), roomNumberDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/delete-room-from-dorm")
    public ResponseEntity<Void> deleteRoomFromDorm(Authentication authentication,
            @RequestBody RoomNumberDto roomNumberDto) {
        TokenDto tokenDto = (TokenDto) authentication.getPrincipal();
        roomService.deleteRoomFromDorm(tokenDto.getEmail(), roomNumberDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/get-students-from-room")
    public ResponseEntity<List<LightUserDto>> getStudentsFromRoom(@RequestBody RoomNumberDto roomNumberDto,
            Authentication authentication) {
        TokenDto tokenDto = (TokenDto) authentication.getPrincipal();
        return ResponseEntity.ok(roomService.getStudentsFromRoom(tokenDto.getEmail(), roomNumberDto));
    }
}
