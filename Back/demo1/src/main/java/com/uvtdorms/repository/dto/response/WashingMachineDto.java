package com.uvtdorms.repository.dto.response;

import com.uvtdorms.repository.entity.WashingMachine;
import com.uvtdorms.repository.entity.enums.StatusMachine;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WashingMachineDto {

    private String id;
    private String name;
    private Boolean isAvailable;

    public WashingMachineDto(WashingMachine washingMachine ){
        this.id=washingMachine.getMachineId().toString();
        this.name=washingMachine.getMachineNumber();
        this.isAvailable=washingMachine.getStatus().equals(StatusMachine.FUNCTIONAL);

    }
}

