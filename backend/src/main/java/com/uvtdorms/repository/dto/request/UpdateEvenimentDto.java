package com.uvtdorms.repository.dto.request;

import java.time.LocalDateTime;

public record UpdateEvenimentDto(
        String id,
        String title,
        String description,
        Boolean canPeopleAttend,
        LocalDateTime startDate) {
}
