package com.uvtdorms.services;

import java.nio.CharBuffer;

import com.uvtdorms.exception.AppException;
import com.uvtdorms.repository.dto.TokenDto;
import com.uvtdorms.repository.dto.request.CredentialsDto;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.uvtdorms.repository.IUserRepository;
import com.uvtdorms.repository.entity.User;
import com.uvtdorms.services.interfaces.IUserService;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public TokenDto login(CredentialsDto credentialsDto) {
        User user = userRepository.getByEmail(credentialsDto.email())
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.password()), user.getPassword())) {
            return modelMapper.map(user, TokenDto.class);
        }
        throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
    }

    public TokenDto verifyToken(TokenDto token) {
        userRepository.getByEmail(token.getEmail())
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        return token;
    }
}
