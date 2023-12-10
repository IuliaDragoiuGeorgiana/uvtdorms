package com.uvtdorms.repository.dto.response;

import com.uvtdorms.repository.dto.response.interfaces.IUserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements IUserDto {
    private Integer userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
}
