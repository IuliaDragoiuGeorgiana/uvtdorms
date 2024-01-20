package com.uvtdorms.services;

import com.uvtdorms.exception.StudentNotFoundException;
import com.uvtdorms.exception.UserNotFoundException;
import com.uvtdorms.repository.IDormRepository;
import com.uvtdorms.repository.IRoomRepository;
import com.uvtdorms.repository.IStudentDetailsRepository;
import com.uvtdorms.repository.IUserRepository;
import com.uvtdorms.repository.dto.response.DormIdDto;
import com.uvtdorms.repository.dto.response.UserDto;
import com.uvtdorms.repository.entity.StudentDetails;
import com.uvtdorms.repository.entity.User;
import com.uvtdorms.services.interfaces.IStudentDetailsService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service

public class StudentDetailsService implements IStudentDetailsService {


    IStudentDetailsRepository studentDetailsRepository;
    ModelMapper modelMapper;
    IUserRepository userRepository;
    IDormRepository dormRepository;

    IRoomRepository roomRepository;

    public StudentDetailsService(IStudentDetailsRepository studentDetailsRepository,
                                 ModelMapper modelMapper,
                                 IUserRepository userRepository,
                                 IDormRepository dormRepository,
                                 IRoomRepository roomRepository) {
        this.studentDetailsRepository = studentDetailsRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.dormRepository = dormRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public DormIdDto getStudentDormId(String email) throws Exception{

        Optional<User>user=userRepository.getByEmail(email);
        if(user.isEmpty()){
            throw new UserNotFoundException();
        }
        Optional<StudentDetails> studentDetails =studentDetailsRepository.findByUser(user.get());
        if(studentDetails.isEmpty()){
            throw new StudentNotFoundException();
        }

        DormIdDto dormIdDto=new DormIdDto();
        dormIdDto.setId(studentDetails.get().getRoom().getDorm().getDormId().toString());

        return  dormIdDto;
    }
}
