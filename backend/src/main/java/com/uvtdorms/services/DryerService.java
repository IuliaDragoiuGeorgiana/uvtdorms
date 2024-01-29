package com.uvtdorms.services;

import com.uvtdorms.exception.DormNotFoundException;
import com.uvtdorms.exception.WrongUuidException;
import com.uvtdorms.repository.IDormRepository;
import com.uvtdorms.repository.IDryerRepository;
import com.uvtdorms.repository.dto.response.DryerDto;
import com.uvtdorms.repository.entity.Dorm;
import com.uvtdorms.repository.entity.Dryer;
import com.uvtdorms.services.interfaces.IDryerService;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DryerService implements IDryerService {

    private final IDormRepository dormRepository;
    private final IDryerRepository dryerRepository;
    private final ModelMapper modelMapper;


    public DryerService(
            IDormRepository dormRepository,
            IDryerRepository dryerRepository,
            ModelMapper modelMapper)
    {
        this.dormRepository = dormRepository;
        this.dryerRepository = dryerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<DryerDto> getDryerFromDorm(String dormId) throws Exception {
        UUID dormUuid = UUID.fromString(dormId);

        if(dormUuid == null)
            throw new WrongUuidException();

        Optional<Dorm> dorm = dormRepository.findById(dormUuid);
        if(dorm.isEmpty()){
            throw new DormNotFoundException();
        }

        List<Dryer> dryers = dryerRepository.findByDorm(dorm.get());
        return dryers.stream()
                .map(dryer -> modelMapper.map(dryer, DryerDto.class))
                .collect(Collectors.toList());
    }
}
