package com.uvtdorms.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.uvtdorms.repository.IRegisterRequestRepository;
import com.uvtdorms.repository.dto.response.RegisterRequestDto;
import com.uvtdorms.repository.entity.Dorm;
import com.uvtdorms.repository.entity.RegisterRequest;
import com.uvtdorms.repository.entity.enums.RegisterRequestStatus;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegisterRequestService {
    private final IRegisterRequestRepository registerRequestRepository;
    private final ModelMapper modelMapper;

    public List<RegisterRequestDto> getRegisterRequestsFromDorm(Dorm dorm) {
        List<RegisterRequest> registerRequests = registerRequestRepository.findByRoomDorm(dorm);

        return registerRequests.stream()
                .filter(request -> request.getStatus() == RegisterRequestStatus.RECEIVED)
                .map(request -> modelMapper.map(request, RegisterRequestDto.class))
                .collect(Collectors.toList());
    }
}
