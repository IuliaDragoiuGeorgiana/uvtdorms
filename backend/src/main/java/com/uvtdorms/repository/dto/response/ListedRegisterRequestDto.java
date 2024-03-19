package com.uvtdorms.repository.dto.response;

import java.time.LocalDate;

import com.uvtdorms.repository.entity.enums.RegisterRequestStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListedRegisterRequestDto {
    private LocalDate createdOn;
    private String dormitoryName;
    private String roomNumber;
    private String administratorContact;
    private RegisterRequestStatus status;
}
