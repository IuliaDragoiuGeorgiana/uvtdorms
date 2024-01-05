package com.uvtdorms.repository.dto.response;

import com.uvtdorms.repository.entity.Dryer;
import com.uvtdorms.repository.entity.enums.StatusMachine;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
 public  class DryerDto {
    private String id;
    private String name;
    private Boolean isAvailable;

    public DryerDto(Dryer dryer){
        this.id=dryer.getDryerId().toString();
        this.name=dryer.getDryerNumber();
        this.isAvailable=dryer.getStatus().equals(StatusMachine.FUNCTIONAL);
    }
}
