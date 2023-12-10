package com.uvtdorms.services;

import com.uvtdorms.repository.IUserRepository;
import com.uvtdorms.repository.dto.response.UserDto;
import com.uvtdorms.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class UserService implements IUserService {
    @Autowired
    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public List<UserDto> getAllUsers()
    {
        List<UserDto> usersList = new ArrayList<>();



        return usersList;
    }
}
