package com.uvtdorms.services;

import com.uvtdorms.exception.AppException;
import com.uvtdorms.repository.IDormRepository;
import com.uvtdorms.repository.IDryerRepository;
import com.uvtdorms.repository.IUserRepository;
import com.uvtdorms.repository.IWashingMachineRepository;
import com.uvtdorms.repository.dto.request.NewMachineDto;
import com.uvtdorms.repository.dto.response.AvailableDryerDto;
import com.uvtdorms.repository.dto.response.DryerDto;
import com.uvtdorms.repository.entity.Dorm;
import com.uvtdorms.repository.entity.DormAdministratorDetails;
import com.uvtdorms.repository.entity.Dryer;
import com.uvtdorms.repository.entity.User;
import com.uvtdorms.repository.entity.WashingMachine;
import com.uvtdorms.repository.entity.enums.StatusMachine;
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
    private final IWashingMachineRepository washingMachineRepository;
    private final IUserRepository userRepository;
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

    public List<AvailableDryerDto> getAvailableDryerFromDorm(String administratorEmail) throws AppException {
        User user = userRepository.getByEmail(administratorEmail)
                .orElseThrow(() -> new AppException("User not found", HttpStatus.NOT_FOUND));

        DormAdministratorDetails dormAdministratorDetails = user.getDormAdministratorDetails();
        if (dormAdministratorDetails == null) {
            throw new AppException("User is not a dorm administrator", HttpStatus.BAD_REQUEST);
        }

        Dorm dorm = dormAdministratorDetails.getDorm();
        List<Dryer> dryers = dryerRepository.findByDormAndAssociatedWashingMachineIsNull(dorm);

        return dryers.stream()
                .map(dryer -> modelMapper.map(dryer, AvailableDryerDto.class))
                .collect(Collectors.toList());
    }

    public void createNewDryer(String administratorEmail, NewMachineDto newDryerDto) {

        User user = userRepository.getByEmail(administratorEmail)
                .orElseThrow(() -> new AppException("User not found", HttpStatus.NOT_FOUND));

        DormAdministratorDetails dormAdministratorDetails = user.getDormAdministratorDetails();
        if (dormAdministratorDetails == null) {
            throw new AppException("User is not a dorm administrator", HttpStatus.BAD_REQUEST);
        }

        Dorm dorm = dormAdministratorDetails.getDorm();
        Dryer newDryer = Dryer.builder().dorm(dorm).dryerNumber(newDryerDto.name()).status(StatusMachine.FUNCTIONAL)
                .build();
        if (!newDryerDto.associatedDryerOrWashingMachineId().equals("")) {
            UUID associatedMachineUuid = UUID.fromString(newDryerDto.associatedDryerOrWashingMachineId());
            WashingMachine washingMachine = washingMachineRepository.findById(associatedMachineUuid)
                    .orElseThrow(() -> new AppException("Washing machine not found", HttpStatus.NOT_FOUND));
            newDryer.setWashingMachine(washingMachine);
        }
        dryerRepository.save(newDryer);

    }
}
