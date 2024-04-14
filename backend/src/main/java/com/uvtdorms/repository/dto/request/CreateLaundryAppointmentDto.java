package com.uvtdorms.repository.dto.request;

import java.time.LocalDate;
import java.util.UUID;

public record CreateLaundryAppointmentDto(UUID selectedMachineId, UUID selectedDryerId, LocalDate selectedDate,
        Integer selectedInterval) {
}
