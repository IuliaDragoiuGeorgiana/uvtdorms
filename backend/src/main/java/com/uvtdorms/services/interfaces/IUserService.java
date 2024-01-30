package com.uvtdorms.services.interfaces;

import com.uvtdorms.repository.dto.response.EmailDto;

public interface IUserService {
    public EmailDto getTestUser() throws Exception;
}
