package com.uvtdorms.services;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.uvtdorms.exception.UserNotFoundException;
import com.uvtdorms.repository.IUserRepository;
import com.uvtdorms.repository.dto.response.UserDto;
import com.uvtdorms.repository.entity.User;
import com.uvtdorms.services.interfaces.IUserService;

@Service
public class UserService implements IUserService {
    private final IUserRepository userRepository;
    private final ModelMapper modelMapper;

    UserService(
        IUserRepository userRepository,
        ModelMapper modelMapper
    )
    {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDto getTestUser() throws Exception
    {
        Optional<User> user = userRepository.getByEmail("iulia.dragoiu02@e-uvt.ro");
        if(user.isEmpty())
        {
            throw new UserNotFoundException();
        }

        return modelMapper.map(user, UserDto.class);
    }
}
