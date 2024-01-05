package com.uvtdorms.services.interfaces;

import com.uvtdorms.repository.dto.response.WashingMachineDto;
import com.uvtdorms.repository.entity.WashingMachine;

import java.time.LocalDate;
import java.util.List;

public interface IWashingMachineService {
   public List<WashingMachineDto> getWashingMachinesFromDorm(String dormId) throws Exception;

}
