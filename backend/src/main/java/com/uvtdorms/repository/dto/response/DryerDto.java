package com.uvtdorms.repository.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
 public  class DryerDto {
    private String id;
    private String name;
    private Boolean isAvailable;
}
