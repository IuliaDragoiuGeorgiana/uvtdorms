package com.uvtdorms.services;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.uvtdorms.exception.AppException;
import com.uvtdorms.repository.IRoomRepository;
import com.uvtdorms.repository.IUserRepository;
import com.uvtdorms.repository.dto.request.RoomNumberDto;
import com.uvtdorms.repository.dto.response.DetailedRoomDto;
import com.uvtdorms.repository.dto.response.LightUserDto;
import com.uvtdorms.repository.dto.response.RoomsNumbersDto;
import com.uvtdorms.repository.entity.DormAdministratorDetails;
import com.uvtdorms.repository.entity.Room;
import com.uvtdorms.repository.entity.User;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final IRoomRepository roomRepository;
    private final IUserRepository userRepository;
    private final ModelMapper modelMapper;

    public RoomsNumbersDto getRoomsNumbersFromDorm(String dormName) {
        List<String> roomsNumbers = new ArrayList<>();
        List<Room> rooms = roomRepository.findByDormDormName(dormName);

        rooms.stream().forEach((room) -> {
            roomsNumbers.add(room.getRoomNumber());
        });

        return new RoomsNumbersDto(roomsNumbers);
    }

    private DormAdministratorDetails getDormAdministratorDetailsByEmail(String dormAdministratorEmail) {
        User user = userRepository.getByEmail(dormAdministratorEmail)
                .orElseThrow(() -> new AppException("User not found", HttpStatus.NOT_FOUND));

        DormAdministratorDetails dormAdministratorDetails = user.getDormAdministratorDetails();

        if (dormAdministratorDetails == null) {
            throw new AppException("User is not a dorm administrator", HttpStatus.BAD_REQUEST);
        }

        return dormAdministratorDetails;
    }

    @Transactional
    public List<DetailedRoomDto> getRoomsDetailesFromDorm(String dormAdministratorEmail) {
        DormAdministratorDetails dormAdministratorDetails = getDormAdministratorDetailsByEmail(dormAdministratorEmail);

        List<Room> rooms = roomRepository.findByDormDormName(dormAdministratorDetails.getDorm().getDormName());

        return rooms.stream().map(room -> modelMapper.map(room, DetailedRoomDto.class)).toList();
    }

    public void addNewRoomToDorm(String dormAdministratorEmail, RoomNumberDto addNewRoomDto) {
        DormAdministratorDetails dormAdministratorDetails = getDormAdministratorDetailsByEmail(dormAdministratorEmail);

        Room room = Room.builder()
                .roomNumber(addNewRoomDto.roomNumber())
                .dorm(dormAdministratorDetails.getDorm())
                .build();

        roomRepository.save(room);
    }

    @Transactional
    public void deleteRoomFromDorm(String dormAdministratorEmail, RoomNumberDto roomNumberDto) {
        DormAdministratorDetails dormAdministratorDetails = getDormAdministratorDetailsByEmail(dormAdministratorEmail);

        Room room = roomRepository
                .findByDormDormNameAndRoomNumber(dormAdministratorDetails.getDorm().getDormName(),
                        roomNumberDto.roomNumber())
                .orElseThrow(() -> new AppException("Room not found", HttpStatus.NOT_FOUND));

        if (room.getNumberOfStudents() > 0) {
            throw new AppException("Room is not empty", HttpStatus.BAD_REQUEST);
        }

        roomRepository.delete(room);
    }

    @Transactional
    public List<LightUserDto> getStudentsFromRoom(String dormAdministratorEmail, RoomNumberDto roomNumberDto) {
        DormAdministratorDetails dormAdministratorDetails = getDormAdministratorDetailsByEmail(dormAdministratorEmail);

        Room room = roomRepository
                .findByDormDormNameAndRoomNumber(dormAdministratorDetails.getDorm().getDormName(),
                        roomNumberDto.roomNumber())
                .orElseThrow(() -> new AppException("Room not found", HttpStatus.NOT_FOUND));

        if (room.getStudentDetails() == null) {
            return new ArrayList<>();
        }

        return room.getStudentDetails().stream()
                .map(studentDetails -> modelMapper.map(studentDetails, LightUserDto.class))
                .toList();
    }
}
