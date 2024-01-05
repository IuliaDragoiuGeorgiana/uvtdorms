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

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class WashingMachineService implements IWashingMachineService {
    private final IWashingMachineRepository washingMachineRepository;
    private final IDormRepository dormRepository;
    private final ModelMapper modelMapper;

    public WashingMachineService(
            IWashingMachineRepository washingMachineRepository,
            IDormRepository dormRepository,
            ModelMapper modelMapper,
            ILaundryAppointmentRepository laundryAppointmentRepository)
    {
        this.dormRepository = dormRepository;
        this.washingMachineRepository = washingMachineRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<WashingMachineDto> getWashingMachinesFromDorm(String dormId) throws Exception {
        Optional<Dorm> dorm = dormRepository.findById(UUID.fromString(dormId));
        if(dorm.isEmpty()){
            throw new DormNotFoundException();
        }

        List<WashingMachine> washingMachines = washingMachineRepository.findByDorm(dorm.get());
        return washingMachines.stream()
                .map(machine -> modelMapper.map(machine, WashingMachineDto.class))
                .collect(Collectors.toList());
    }
}
