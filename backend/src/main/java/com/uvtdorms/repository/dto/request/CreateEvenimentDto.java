package com.uvtdorms.repository.dto.request;

import java.time.LocalDateTime;

public record CreateEvenimentDto(
        String title,
        String description,
        Boolean canPeopleAttend,
        LocalDateTime startDate) {
}
