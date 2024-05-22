package com.uvtdorms.repository.dto.request;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.eclipse.angus.mail.imap.protocol.Status;

import com.uvtdorms.repository.entity.enums.StatusTicket;
import com.uvtdorms.repository.entity.enums.TipInterventie;

public record CreateTicketDto( LocalDateTime creationDate, StatusTicket statusTicket, TipInterventie tipInterventie, String title, String description, boolean alreadyAnuncement) {
}
