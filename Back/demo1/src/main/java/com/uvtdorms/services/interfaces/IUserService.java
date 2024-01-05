package com.uvtdorms.services.interfaces;

import com.uvtdorms.repository.dto.response.UserDto;

public interface IUserService {
    public UserDto getTestUser() throws Exception;
}
