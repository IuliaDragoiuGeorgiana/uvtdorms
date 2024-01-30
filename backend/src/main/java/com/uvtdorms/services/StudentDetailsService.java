package com.uvtdorms.services;

import com.uvtdorms.exception.AppException;
import com.uvtdorms.repository.IStudentDetailsRepository;
import com.uvtdorms.repository.IUserRepository;
import com.uvtdorms.repository.dto.response.DormIdDto;
import com.uvtdorms.repository.entity.StudentDetails;
import com.uvtdorms.repository.entity.User;
import com.uvtdorms.services.interfaces.IStudentDetailsService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentDetailsService implements IStudentDetailsService {


    private final IStudentDetailsRepository studentDetailsRepository;
    private final IUserRepository userRepository;


    @Override
    public DormIdDto getStudentDormId(String email) throws AppException{
        User user = userRepository.getByEmail(email)
                .orElseThrow(() -> new AppException("User not found", HttpStatus.NOT_FOUND));

        StudentDetails studentDetails = studentDetailsRepository.findByUser(user)
                .orElseThrow(() -> new AppException("User is not a student", HttpStatus.BAD_REQUEST));

        DormIdDto dormIdDto = DormIdDto.builder()
                .id(studentDetails.getRoom().getDorm().getDormId().toString())
                .build();

        return  dormIdDto;
    }
}
