package com.uvtdorms.services;

import com.uvtdorms.repository.IDormRepository;
import com.uvtdorms.repository.ILaundryAppointmentRepository;
import com.uvtdorms.repository.IWashingMachineRepository;
import com.uvtdorms.repository.entity.WashingMachine;
import com.uvtdorms.services.interfaces.IWashingMachineService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//@Service
//public class WashingMachineService implements IWashingMachineService {
//
//
//    private  final IWashingMachineRepository iWashingMachineRepository;
//    private  final IDormRepository dormRepository;
//    private  final ModelMapper modelMapper;
//
//    private  final ILaundryAppointmentRepository iLaundryAppointmentRepository;
//
//    public WashingMachineService(IWashingMachineRepository iWashingMachineRepository,IDormRepository dormRepository,ModelMapper modelMapper,ILaundryAppointmentRepository iLaundryAppointmentRepository){
//        this.dormRepository=dormRepository;
//        this.iWashingMachineRepository=iWashingMachineRepository;
//        this.iLaundryAppointmentRepository=iLaundryAppointmentRepository;
//        this.modelMapper=modelMapper;
//    }
//
//    @Override
//    public WashingMachine addWashingMachine(WashingMachine washingMachine) {
//        return iWashingMachineRepository.save(washingMachine);
//    }
//
//    @Override
//    public void deleteWashingMachine(Long machineId) {
//        WashingMachine washingMachine=getWashingMachineById(machineId);
//        iWashingMachineRepository.deleteById(machineId);
//
//    }
//    @Override
//    public WashingMachine getWashingMachineById(Long washingMachineID){
//        Optional<WashingMachine> washingMachine = iWashingMachineRepository.findById(washingMachineID);
//        if (washingMachine.isPresent()) {
//            return washingMachine .get();
//        }
//        return new WashingMachine();
//    }
//
//
//    @Override
//    public List<WashingMachine> getAllWashingMachines() {
//        return iWashingMachineRepository.findAll();
//    }







