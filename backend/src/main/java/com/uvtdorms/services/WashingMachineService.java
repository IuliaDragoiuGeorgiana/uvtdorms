package com.uvtdorms.services;

import com.uvtdorms.exception.AppException;
import com.uvtdorms.repository.IDormRepository;
import com.uvtdorms.repository.IUserRepository;
import com.uvtdorms.repository.IWashingMachineRepository;
import com.uvtdorms.repository.dto.response.AvailableWashingMachineDto;
import com.uvtdorms.repository.dto.response.WashingMachineDto;
import com.uvtdorms.repository.entity.Dorm;
import com.uvtdorms.repository.entity.DormAdministratorDetails;
import com.uvtdorms.repository.entity.User;
import com.uvtdorms.repository.entity.WashingMachine;
import com.uvtdorms.services.interfaces.IWashingMachineService;

import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WashingMachineService implements IWashingMachineService {
    private final IWashingMachineRepository washingMachineRepository;
    private final IDormRepository dormRepository;
    private final IUserRepository  userRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<WashingMachineDto> getWashingMachinesFromDorm(String dormId) throws AppException {
        UUID dormUuid = UUID.fromString(dormId);
        if (dormUuid == null) {
            throw new AppException("Wrong UUID", HttpStatus.BAD_REQUEST);
        }

        Dorm dorm = dormRepository.findById(dormUuid)
                .orElseThrow(() -> new AppException("Dorm not found", HttpStatus.NOT_FOUND));

        List<WashingMachine> washingMachines = washingMachineRepository.findByDorm(dorm);
        return washingMachines.stream()
                .map(machine -> modelMapper.map(machine, WashingMachineDto.class))
                .collect(Collectors.toList());
    }

    public List<AvailableWashingMachineDto> getAvailableWashingMachinesFromDorm(String administratorEmail) throws AppException {
       
        User user = userRepository.getByEmail(administratorEmail)
                .orElseThrow(() -> new AppException("User not found", HttpStatus.NOT_FOUND));
        DormAdministratorDetails dormAdministratorDetails = user.getDormAdministratorDetails();
        if (dormAdministratorDetails == null) {
            throw new AppException("User is not a dorm administrator", HttpStatus.BAD_REQUEST);
        }
        Dorm dorm = dormAdministratorDetails.getDorm();

        List<WashingMachine> washingMachines = washingMachineRepository.findByDormAndAssociatedDryerIsNull(dorm);
        return washingMachines.stream()
                .map(machine -> modelMapper.map(machine, AvailableWashingMachineDto.class))
                .collect(Collectors.toList());
    }

}
