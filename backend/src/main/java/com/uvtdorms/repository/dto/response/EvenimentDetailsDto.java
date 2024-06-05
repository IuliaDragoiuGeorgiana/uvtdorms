package com.uvtdorms.repository.dto.response;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class EvenimentDetailsDto {
    String id;
    String title;
    String description;
    String dormAdministratorEmail;
    String dormAdministratorName;
    Boolean canPeopleAttend;
    Boolean isUserAttending;
    Integer numberOfAttendees;
    LocalDateTime startDate;
}
