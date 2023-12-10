package com.uvtdorms.services.interfaces;

import com.uvtdorms.repository.dto.response.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IUserService {
    public List<UserDto> getAllUsers();
}
