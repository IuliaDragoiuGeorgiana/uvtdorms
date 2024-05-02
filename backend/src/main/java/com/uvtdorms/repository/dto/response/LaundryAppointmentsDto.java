package com.uvtdorms.repository.dto.response;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class LaundryAppointmentsDto {
    private String studentEmail;
    private String washingMachineId;
    private String dryerId;
    private LocalDateTime intervalBeginDate;
}
