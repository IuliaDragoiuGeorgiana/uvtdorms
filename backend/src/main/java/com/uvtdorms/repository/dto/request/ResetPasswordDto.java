package com.uvtdorms.repository.dto.request;

public record ResetPasswordDto(String token, String newPassword) {

}
