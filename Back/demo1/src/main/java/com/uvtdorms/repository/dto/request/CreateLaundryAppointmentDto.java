package com.uvtdorms.repository.dto.request;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor

public class CreateLaundryAppointmentDto {

    private String userEmail;

    private UUID selectedMachineId;

    private UUID selectedDryerId;

    private LocalDate selectedDate;

    private Integer selectedInterval;
}
