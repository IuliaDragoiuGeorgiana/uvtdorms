package com.uvtdorms.services;

import com.uvtdorms.exception.AppException;
import com.uvtdorms.repository.IDormRepository;
import com.uvtdorms.repository.IRoomRepository;
import com.uvtdorms.repository.IStudentDetailsRepository;
import com.uvtdorms.repository.IUserRepository;
import com.uvtdorms.repository.dto.request.RegisterStudentDto;
import com.uvtdorms.repository.dto.response.DormIdDto;
import com.uvtdorms.repository.entity.Dorm;
import com.uvtdorms.repository.entity.Room;
import com.uvtdorms.repository.entity.StudentDetails;
import com.uvtdorms.repository.entity.User;
import com.uvtdorms.repository.entity.enums.Role;
import com.uvtdorms.services.interfaces.IStudentDetailsService;
import com.uvtdorms.utils.PasswordGenerator;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentDetailsService implements IStudentDetailsService {
        private final IStudentDetailsRepository studentDetailsRepository;
        private final IUserRepository userRepository;
        private final IRoomRepository roomRepository;
        private final IDormRepository dormRepository;
        private final PasswordEncoder passwordEncoder;

        @Override
        public DormIdDto getStudentDormId(String email) throws AppException {
                User user = userRepository.getByEmail(email)
                                .orElseThrow(() -> new AppException("User not found", HttpStatus.NOT_FOUND));

                StudentDetails studentDetails = studentDetailsRepository.findByUser(user)
                                .orElseThrow(() -> new AppException("User is not a student", HttpStatus.BAD_REQUEST));

                DormIdDto dormIdDto = DormIdDto.builder()
                                .id(studentDetails.getRoom().getDorm().getDormId().toString())
                                .build();

                return dormIdDto;
        }

        public void registerStudent(RegisterStudentDto registerStudentDto) throws AppException {
                if (userRepository.getByEmail(registerStudentDto.email()).isPresent())
                        throw new AppException("Account already exists with this email.", HttpStatus.CONFLICT);

                if (studentDetailsRepository.findByMatriculationNumber(registerStudentDto.matriculationNumber())
                                .isPresent())
                        throw new AppException("Student already exists.", HttpStatus.CONFLICT);

                if (userRepository.findByPhoneNumber(registerStudentDto.phoneNumber()).isPresent())
                        throw new AppException("Phone number already in use.", HttpStatus.CONFLICT);

                Dorm dorm = dormRepository.getByDormName(registerStudentDto.dormName());
                Room room = roomRepository.findByDormAndRoomNumber(dorm, registerStudentDto.roomNumber());

                String generatedPassword = PasswordGenerator.generateRandomPassword();

                User user = User.builder()
                                .firstName(registerStudentDto.firtName())
                                .lastName(registerStudentDto.lastName())
                                .email(registerStudentDto.email())
                                .phoneNumber(registerStudentDto.email())
                                .password(passwordEncoder.encode(generatedPassword))
                                .role(Role.STUDENT)
                                .isActive(false)
                                .build();

                userRepository.save(user);

                StudentDetails student = new StudentDetails(
                                registerStudentDto.matriculationNumber(),
                                user,
                                room);

                studentDetailsRepository.save(student);

                user.setStudentDetails(student);
                userRepository.save(user);

                room.getStudentDetails().add(student);
                roomRepository.save(room);
        }
}
