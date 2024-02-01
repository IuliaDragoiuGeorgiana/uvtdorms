package com.uvtdorms.repository.dto.request;

public record RegisterStudentDto(
                String email,
                String firtName,
                String lastName,
                String dormName,
                String roomNumber,
                String matriculationNumber,
                String phoneNumber) {
}
