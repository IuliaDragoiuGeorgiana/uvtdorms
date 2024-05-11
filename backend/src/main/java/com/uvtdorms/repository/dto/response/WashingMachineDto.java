package com.uvtdorms.repository.dto.response;

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
    private String associatedDryerId;
    private StatusMachine statusMachine;
}
