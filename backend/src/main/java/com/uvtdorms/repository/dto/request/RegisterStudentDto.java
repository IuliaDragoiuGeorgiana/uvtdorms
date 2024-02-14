package com.uvtdorms.repository.dto.request;

public record RegisterStudentDto(
                String email,
            String firstName,
                String lastName,
                String dormName,
                String roomNumber,
                String matriculationNumber,
                String phoneNumber) {
}
