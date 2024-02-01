package com.uvtdorms.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.uvtdorms.repository.IRoomRepository;
import com.uvtdorms.repository.dto.response.RoomsNumbersDto;
import com.uvtdorms.repository.entity.Room;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final IRoomRepository roomRepository;

    public RoomsNumbersDto getRoomsNumbersFromDorm(String dormName) {
        List<String> roomsNumbers = new ArrayList<>();
        List<Room> rooms = roomRepository.findByDormDormName(dormName);

        rooms.stream().forEach((room) -> {
            roomsNumbers.add(room.getRoomNumber());
        });

        return new RoomsNumbersDto(roomsNumbers);
    }
}
