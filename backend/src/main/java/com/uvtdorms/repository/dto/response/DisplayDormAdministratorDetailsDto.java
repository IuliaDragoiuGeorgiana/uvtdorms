package com.uvtdorms.repository.dto.response;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DisplayDormAdministratorDetailsDto {
    private String email;
    private String phoneNumber;
    private String dormName;
}
