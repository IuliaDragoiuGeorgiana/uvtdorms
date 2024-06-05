package com.uvtdorms.repository.dto.response;

import java.time.LocalDateTime;

import com.uvtdorms.repository.entity.enums.StatusTicket;
import com.uvtdorms.repository.entity.enums.TipInterventie;

import lombok.Data;

@Data
public class TicketDto {
    String id;
    LocalDateTime creationDate;
    StatusTicket statusTicket;
    TipInterventie tipInterventie;
    String title;
    String description;
    boolean alreadyAnuncement;
    String studentEmail;
    String roomNumber;
}
