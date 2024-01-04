package com.uvtdorms.services;

import com.uvtdorms.exception.DryerNotFoundException;
import com.uvtdorms.exception.UserNotFoundException;
import com.uvtdorms.exception.WashingMachineNotFoundException;
import com.uvtdorms.repository.IDryerRepository;
import com.uvtdorms.repository.ILaundryAppointmentRepository;
import com.uvtdorms.repository.IUserRepository;
import com.uvtdorms.repository.IWashingMachineRepository;
import com.uvtdorms.repository.dto.request.CreateLaundryAppointmentDto;
import com.uvtdorms.repository.entity.Dryer;
import com.uvtdorms.repository.entity.LaundryAppointment;
import com.uvtdorms.repository.entity.User;
import com.uvtdorms.repository.entity.WashingMachine;
import com.uvtdorms.services.interfaces.ILaundryAppointmentService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class LaundryAppointmentService implements ILaundryAppointmentService {
    private final IUserRepository userRepository;
    private final IWashingMachineRepository washingMachineRepository;
    private final IDryerRepository dryerRepository;
    private final ILaundryAppointmentRepository laundryAppointmentRepository;

    public LaundryAppointmentService(IUserRepository userRepository,
                                     ILaundryAppointmentRepository laundryAppointmentRepository,
                                     IDryerRepository dryerRepository,
                                     IWashingMachineRepository washingMachineRepository) {
        this.userRepository = userRepository;
        this.laundryAppointmentRepository = laundryAppointmentRepository;
        this.washingMachineRepository = washingMachineRepository;
        this.dryerRepository = dryerRepository;
    }

    @Override
    public void createLaundryAppointment(CreateLaundryAppointmentDto createLaundryAppointmentDto) throws Exception{

        Optional<User> user = userRepository.getByEmail(createLaundryAppointmentDto.getUserEmail());
        if(user.isEmpty()){
            throw new UserNotFoundException();
        }
        Optional<WashingMachine> washingMachine = washingMachineRepository.findById(createLaundryAppointmentDto.getSelectedMachineId());
        if(washingMachine.isEmpty()){
            throw new WashingMachineNotFoundException();
        }
        Optional<Dryer> dryer = dryerRepository.findById(createLaundryAppointmentDto.getSelectedDryerId());
        if(dryer.isEmpty()){
            throw new DryerNotFoundException();
        }
        LocalDateTime intervalBeginDate = createLaundryAppointmentDto.getSelectedDate().atTime(createLaundryAppointmentDto.getSelectedInterval(),0);
        LaundryAppointment laundryAppointment = new LaundryAppointment(intervalBeginDate,user.get(),washingMachine.get(),dryer.get());
        laundryAppointmentRepository.save(laundryAppointment);

    }


}
