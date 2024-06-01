package com.uvtdorms.repository.dto.request;

public record AddNewDormAdministratorDto(
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        String dormId) {

}
