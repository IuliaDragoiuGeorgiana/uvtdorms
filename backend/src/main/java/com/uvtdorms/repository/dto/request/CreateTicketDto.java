package com.uvtdorms.repository.dto.request;

import com.uvtdorms.repository.entity.enums.TipInterventie;

public record CreateTicketDto(TipInterventie tipInterventie,
        String title, String description, boolean alreadyAnuncement) {
}
