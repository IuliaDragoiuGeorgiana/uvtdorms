package com.uvtdorms.repository.dto.request;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FreeIntervalDto {
    String dormId;
    LocalDate date;
    String washingMachineId;
    String dryerId;
}
