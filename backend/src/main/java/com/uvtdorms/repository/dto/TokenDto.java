package com.uvtdorms.repository.dto;

import com.uvtdorms.repository.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenDto {
    private String email;
    private String token;
    private Role role;
}
