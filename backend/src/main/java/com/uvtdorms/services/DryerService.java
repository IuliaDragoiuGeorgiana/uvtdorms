package com.uvtdorms.services;

import com.uvtdorms.exception.AppException;
import com.uvtdorms.repository.IDormRepository;
import com.uvtdorms.repository.IDryerRepository;
import com.uvtdorms.repository.dto.response.DryerDto;
import com.uvtdorms.repository.entity.Dorm;
import com.uvtdorms.repository.entity.Dryer;
import com.uvtdorms.services.interfaces.IDryerService;

import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DryerService implements IDryerService {

    private final IDormRepository dormRepository;
    private final IDryerRepository dryerRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<DryerDto> getDryerFromDorm(String dormId) throws AppException {
        UUID dormUuid = UUID.fromString(dormId);

        if (dormUuid == null) {
            throw new AppException("Invalid UUID", HttpStatus.BAD_REQUEST);
        }

        Dorm dorm = dormRepository.findById(dormUuid)
                .orElseThrow(() -> new AppException("Dorm not found", HttpStatus.NOT_FOUND));

        List<Dryer> dryers = dryerRepository.findByDorm(dorm);

        return dryers.stream()
                .map(dryer -> modelMapper.map(dryer, DryerDto.class))
                .collect(Collectors.toList());
    }
}
