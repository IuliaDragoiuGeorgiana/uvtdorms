package com.uvtdorms.repository.dto.response;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.uvtdorms.repository.entity.enums.RegisterRequestStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDto extends StudentDetailsDto {
    private RegisterRequestStatus status;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdOn;
}
