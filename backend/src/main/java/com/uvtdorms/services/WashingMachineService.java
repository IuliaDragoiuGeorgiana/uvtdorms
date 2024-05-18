package com.uvtdorms.services;

import com.uvtdorms.exception.AppException;
import com.uvtdorms.repository.IDormRepository;
import com.uvtdorms.repository.IDryerRepository;
import com.uvtdorms.repository.ILaundryAppointmentRepository;
import com.uvtdorms.repository.IUserRepository;
import com.uvtdorms.repository.IWashingMachineRepository;
import com.uvtdorms.repository.dto.request.NewMachineDto;
import com.uvtdorms.repository.dto.response.AvailableWashingMachineDto;
import com.uvtdorms.repository.dto.response.WashingMachineDto;
import com.uvtdorms.repository.entity.Dorm;
import com.uvtdorms.repository.entity.DormAdministratorDetails;
import com.uvtdorms.repository.entity.Dryer;
import com.uvtdorms.repository.entity.LaundryAppointment;
import com.uvtdorms.repository.entity.User;
import com.uvtdorms.repository.entity.WashingMachine;
import com.uvtdorms.repository.entity.enums.StatusLaundry;
import com.uvtdorms.repository.entity.enums.StatusMachine;
import com.uvtdorms.services.interfaces.IWashingMachineService;

import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WashingMachineService implements IWashingMachineService {
    private final IWashingMachineRepository washingMachineRepository;
    private final IDryerRepository dryerRepository;
    private final IDormRepository dormRepository;
    private final IUserRepository userRepository;
    private final ILaundryAppointmentRepository laundryAppointmentRepository;
    private final ModelMapper modelMapper;
    private final EmailService emailService;

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

    public List<AvailableWashingMachineDto> getAvailableWashingMachinesFromDorm(String administratorEmail)
            throws AppException {
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

    public void createNewWashingMachine(String administratorEmail, NewMachineDto newWashingMachineDto) {
        User user = userRepository.getByEmail(administratorEmail)
                .orElseThrow(() -> new AppException("User not found", HttpStatus.NOT_FOUND));
        DormAdministratorDetails dormAdministratorDetails = user.getDormAdministratorDetails();
        if (dormAdministratorDetails == null) {
            throw new AppException("User is not a dorm administrator", HttpStatus.BAD_REQUEST);
        }
        Dorm dorm = dormAdministratorDetails.getDorm();
        WashingMachine newWashingMachine = WashingMachine.builder().dorm(dorm)
                .machineNumber(newWashingMachineDto.name()).status(StatusMachine.FUNCTIONAL).build();
        if (!newWashingMachineDto.associatedDryerOrWashingMachineId().equals("")) {
            UUID associatedMachineUuid = UUID.fromString(newWashingMachineDto.associatedDryerOrWashingMachineId());
            Dryer dryer = dryerRepository.findById(associatedMachineUuid)
                    .orElseThrow(() -> new AppException("Dryer not found", HttpStatus.NOT_FOUND));
            newWashingMachine.setDryer(dryer);
        }
        washingMachineRepository.save(newWashingMachine);
    }

    private void cancelLaundryAppointmentsForWashingMachineForTheRestOfTheWeek(WashingMachine washingMachine) {
        List<LaundryAppointment> laundryAppointments = laundryAppointmentRepository.findByWashMachine(washingMachine)
                .stream()
                .filter(appointment -> appointment.getIntervalBeginDate().isAfter(LocalDateTime.now()))
                .collect(Collectors.toList());

        for (LaundryAppointment appointment : laundryAppointments) {
            if (appointment.getStatusLaundry() == StatusLaundry.SCHEDULED) {
                appointment.setStatusLaundry(StatusLaundry.CANCELED);
                emailService.sendLaundryAppointmentCanceledBecauseOfWashingMachineFailure(
                        appointment.getStudent().getUser().getEmail(),
                        appointment.getIntervalBeginDate().toString());
            }
        }
    }

    public void updateWashingMachine(final WashingMachineDto washingMachineDto) {
        UUID washingMachineUuid = UUID.fromString(washingMachineDto.getId());
        WashingMachine washingMachine = washingMachineRepository.findById(washingMachineUuid)
                .orElseThrow(() -> new AppException("Washing machine not found", HttpStatus.NOT_FOUND));

        if (washingMachineDto.getName().isEmpty()) {
            throw new AppException("Invalid washing machine name", HttpStatus.BAD_REQUEST);
        }
        washingMachine.setMachineNumber(washingMachineDto.getName());

        washingMachine.setStatus(washingMachineDto.getStatusMachine());
        if (washingMachineDto.getStatusMachine() == StatusMachine.BROKEN) {
            cancelLaundryAppointmentsForWashingMachineForTheRestOfTheWeek(washingMachine);
        }

        if (!washingMachineDto.getAssociatedDryerId().isEmpty()) {
            UUID associatedDryerUuid = UUID.fromString(washingMachineDto.getAssociatedDryerId());
            Dryer dryer = dryerRepository.findById(associatedDryerUuid)
                    .orElseThrow(() -> new AppException("Dryer not found", HttpStatus.NOT_FOUND));
            if (washingMachine.getAssociatedDryer() != null) {
                washingMachine.getAssociatedDryer().setAssociatedWashingMachine(null);
            }
            washingMachine.setDryer(dryer);
        } else {
            if (washingMachine.getAssociatedDryer() != null) {
                washingMachine.getAssociatedDryer().setAssociatedWashingMachine(null);
            }
            washingMachine.setDryer(null);
        }
        washingMachineRepository.save(washingMachine);
    }

}
