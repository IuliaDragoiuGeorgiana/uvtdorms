package com.uvtdorms.services.interfaces;

import com.uvtdorms.repository.dto.response.WashingMachineDto;

import java.util.List;

public interface IWashingMachineService {
   public List<WashingMachineDto> getWashingMachinesFromDorm(String dormId) throws Exception;

}
