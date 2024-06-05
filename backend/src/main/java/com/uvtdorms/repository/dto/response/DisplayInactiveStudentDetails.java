package com.uvtdorms.repository.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DisplayInactiveStudentDetails {
    
    private String email;
    private String phoneNumber;

}
