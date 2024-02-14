package com.uvtdorms.repository.dto.response;

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
public class RegisterRequestDto {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String dormName;
    private String roomNumber;
    private String matriculationNumber;
    private RegisterRequestStatus status;
}
