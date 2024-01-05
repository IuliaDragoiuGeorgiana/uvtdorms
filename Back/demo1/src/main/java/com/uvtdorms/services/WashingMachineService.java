package com.uvtdorms.services;

import com.uvtdorms.exception.DormNotFoundException;
import com.uvtdorms.repository.IDormRepository;
import com.uvtdorms.repository.ILaundryAppointmentRepository;
import com.uvtdorms.repository.IWashingMachineRepository;
import com.uvtdorms.repository.dto.response.WashingMachineDto;
import com.uvtdorms.repository.entity.Dorm;
import com.uvtdorms.repository.entity.WashingMachine;
import com.uvtdorms.services.interfaces.IWashingMachineService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class WashingMachineService implements IWashingMachineService {


    private final IWashingMachineRepository iWashingMachineRepository;
    private final IDormRepository iDormRepository;
    private final ModelMapper modelMapper;


    public WashingMachineService(IWashingMachineRepository iWashingMachineRepository, IDormRepository dormRepository, ModelMapper modelMapper, ILaundryAppointmentRepository iLaundryAppointmentRepository) {
        this.iDormRepository = dormRepository;
        this.iWashingMachineRepository = iWashingMachineRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<WashingMachineDto> getWashingMachinesFromDorm(String dormId) throws Exception {

        Optional<Dorm> dorm = iDormRepository.findById(UUID.fromString(dormId));
        if(dorm.isEmpty()){
            throw new DormNotFoundException();
        }
        List<WashingMachine> washingMachines = iWashingMachineRepository.findByDorm(dorm.get());
        return washingMachines.stream()
                .map(WashingMachineDto::new)
                .collect(Collectors.toList());
    }



}






