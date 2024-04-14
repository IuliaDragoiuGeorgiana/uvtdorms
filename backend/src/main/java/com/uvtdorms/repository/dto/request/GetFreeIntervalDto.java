package com.uvtdorms.repository.dto.request;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString // For testing purposes. Remove this later.
public class GetFreeIntervalDto {
    String dormId;
    LocalDate date;
    String washingMachineId;
    String dryerId;
}
