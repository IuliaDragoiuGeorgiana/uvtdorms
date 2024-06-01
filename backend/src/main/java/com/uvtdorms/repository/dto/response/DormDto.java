package com.uvtdorms.repository.dto.response;

import lombok.Data;

@Data
public class DormDto {
    String id;
    String name;
    String address;
    String administratorEmail;
    String administratorName;
}
