package com.uvtdorms.services.interfaces;

import com.uvtdorms.repository.entity.WashingMachine;

import java.util.List;

public interface IWashingMachineService {

   public WashingMachine addWashingMachine(WashingMachine washingMachine);

   public void deleteWashingMachine(Long machineId);

   public WashingMachine getWashingMachineById(Long washingMachineID);

   public List<WashingMachine> getAllWashingMachines();

}
