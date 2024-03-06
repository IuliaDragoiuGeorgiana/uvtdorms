package com.uvtdorms.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.uvtdorms.exception.AppException;
import com.uvtdorms.repository.IRegisterRequestRepository;
import com.uvtdorms.repository.IRoomRepository;
import com.uvtdorms.repository.IStudentDetailsRepository;
import com.uvtdorms.repository.dto.response.RegisterRequestDto;
import com.uvtdorms.repository.entity.Dorm;
import com.uvtdorms.repository.entity.RegisterRequest;
import com.uvtdorms.repository.entity.Room;
import com.uvtdorms.repository.entity.StudentDetails;
import com.uvtdorms.repository.entity.enums.RegisterRequestStatus;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegisterRequestService {
    private final IRegisterRequestRepository registerRequestRepository;
    private final IStudentDetailsRepository studentDetailsRepository;
    private final IRoomRepository roomRepository;
    private final EmailService emailService;
    private final ModelMapper modelMapper;

    public List<RegisterRequestDto> getRegisterRequestsFromDorm(final Dorm dorm) {
        List<RegisterRequest> registerRequests = registerRequestRepository.findByRoomDorm(dorm);

        return registerRequests.stream()
                .filter(request -> request.getStatus() == RegisterRequestStatus.RECEIVED)
                .map(request -> modelMapper.map(request, RegisterRequestDto.class))
                .collect(Collectors.toList());
    }

    public void acceptRegisterRequest(final RegisterRequestDto registerRequestDto) throws AppException
    {
        RegisterRequest registerRequest = getRegisterRequestByRegisterRequestDto(registerRequestDto);

        if (registerRequest.getStatus() != RegisterRequestStatus.RECEIVED)
        {
            throw new AppException("Invalid registration request!", HttpStatus.BAD_REQUEST);
        }

        registerRequest.setStatus(RegisterRequestStatus.ACCEPTED);

        registerRequestRepository.save(registerRequest);

        emailService.sendRegisterRequestAcceptedEmail(registerRequest.getStudent().getUser().getEmail(), registerRequest.getStudent().getUser().getFirstName() + " " + registerRequest.getStudent().getUser().getLastName());
    }
    
    public void declineRegisterRequest(final RegisterRequestDto registerRequestDto) throws AppException
    {
        RegisterRequest registerRequest = getRegisterRequestByRegisterRequestDto(registerRequestDto);

        if (registerRequest.getStatus() != RegisterRequestStatus.RECEIVED)
        {
            throw new AppException("Invalid registration request!", HttpStatus.BAD_REQUEST);
        }

        registerRequest.setStatus(RegisterRequestStatus.DECLINED);

        registerRequestRepository.save(registerRequest);

        emailService.sendRegisterRequestDeclinedEmail(registerRequest.getStudent().getUser().getEmail(), registerRequest.getStudent().getUser().getFirstName() + " " + registerRequest.getStudent().getUser().getLastName());
    }
    
    private RegisterRequest getRegisterRequestByRegisterRequestDto(final RegisterRequestDto registerRequestDto)
    {
        StudentDetails student = studentDetailsRepository
                .findByMatriculationNumber(registerRequestDto.getMatriculationNumber())
                .orElseThrow(() -> new AppException("Student not found!", HttpStatus.NOT_FOUND));

        Room room = roomRepository.findByDormDormNameAndRoomNumber(registerRequestDto.getDormName(),
                registerRequestDto.getRoomNumber())
                .orElseThrow(() -> new AppException("Room not found!", HttpStatus.NOT_FOUND));

        RegisterRequest registerRequest = registerRequestRepository.findByStudentAndRoom(student, room)
                .orElseThrow(() -> new AppException("Register request not found!", HttpStatus.NOT_FOUND));

        return registerRequest;
    }
}
