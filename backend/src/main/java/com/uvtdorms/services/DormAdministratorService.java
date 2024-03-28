package com.uvtdorms.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.uvtdorms.exception.AppException;
import com.uvtdorms.repository.IDormAdministratorDetailsRepository;
import com.uvtdorms.repository.IUserRepository;
import com.uvtdorms.repository.entity.Dorm;
import com.uvtdorms.repository.entity.DormAdministratorDetails;
import com.uvtdorms.repository.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DormAdministratorService {
    private final IUserRepository userRepository;
    private final IDormAdministratorDetailsRepository dormAdministratorDetailsRepository;

    public Dorm getAdministratorDormByEmail(String email) {
        User user = userRepository.getByEmail(email)
                .orElseThrow(() -> new AppException("User not found.", HttpStatus.NOT_FOUND));
        DormAdministratorDetails dormAdministratorDetails = dormAdministratorDetailsRepository.findByAdministrator(user)
                .orElseThrow(() -> new AppException("User not a dorm administrator.", HttpStatus.BAD_REQUEST));
        
        return dormAdministratorDetails.getDorm();
    }
}
