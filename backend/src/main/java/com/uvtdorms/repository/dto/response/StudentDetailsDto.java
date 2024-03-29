package com.uvtdorms.repository.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDetailsDto extends UserDetailsDto {
    private String dormName;
    private String roomNumber;
    private String matriculationNumber;
}
