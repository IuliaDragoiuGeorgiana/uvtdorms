package com.uvtdorms.services;

import com.uvtdorms.exception.DormNotFoundException;
import com.uvtdorms.repository.IDormRepository;
import com.uvtdorms.repository.IDryerRepository;
import com.uvtdorms.repository.ILaundryAppointmentRepository;
import com.uvtdorms.repository.dto.response.DryerDto;
import com.uvtdorms.repository.dto.response.WashingMachineDto;
import com.uvtdorms.repository.entity.Dorm;
import com.uvtdorms.repository.entity.Dryer;
import com.uvtdorms.repository.entity.WashingMachine;
import com.uvtdorms.services.interfaces.IDryerService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DryerService implements IDryerService {

    private final IDormRepository iDormRepository;
    private final IDryerRepository iDryerRepository;
    private final ModelMapper modelMapper;


    public DryerService(IDormRepository iDormRepository,
                        IDryerRepository iDryerRepository,
                        ModelMapper modelMapper,
                        ILaundryAppointmentRepository iLaundryAppointmentRepository) {
        this.iDormRepository = iDormRepository;
        this.iDryerRepository = iDryerRepository;
        this.modelMapper = modelMapper;

    }

    @Override
    public List<DryerDto> getDryerFromDorm(String dormId) throws Exception {

        Optional<Dorm> dorm = iDormRepository.findById(UUID.fromString(dormId));
        if(dorm.isEmpty()){
            throw new DormNotFoundException();
        }
        List<Dryer> dryers = iDryerRepository.findByDorm(dorm.get());
        return dryers.stream()
                .map(DryerDto::new)
                .collect(Collectors.toList());
    }
}
