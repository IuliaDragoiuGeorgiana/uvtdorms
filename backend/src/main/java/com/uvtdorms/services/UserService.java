package com.uvtdorms.services;

import java.nio.CharBuffer;
import java.util.Optional;

import com.uvtdorms.exception.AppException;
import com.uvtdorms.repository.dto.TokenDto;
import com.uvtdorms.repository.dto.request.CredentialsDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.uvtdorms.exception.UserNotFoundException;
import com.uvtdorms.repository.IUserRepository;
import com.uvtdorms.repository.dto.response.EmailDto;
import com.uvtdorms.repository.entity.User;
import com.uvtdorms.services.interfaces.IUserService;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Override
    public EmailDto getTestUser() throws Exception
    {
        Optional<User> user = userRepository.getByEmail("iulia.dragoiu02@e-uvt.ro");
        if(user.isEmpty())
        {
            throw new UserNotFoundException();
        }

        return modelMapper.map(user, EmailDto.class);
    }

    public TokenDto login(CredentialsDto credentialsDto)
    {
        User user = userRepository.getByEmail(credentialsDto.email())
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        if(passwordEncoder.matches(CharBuffer.wrap(credentialsDto.password()), user.getPassword()))
        {
            return modelMapper.map(user, TokenDto.class);
        }
        throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
    }

    public TokenDto verifyToken(TokenDto token)
    {
        User user = userRepository.getByEmail(token.getEmail())
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        return token;
    }
}
