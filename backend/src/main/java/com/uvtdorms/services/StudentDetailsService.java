package com.uvtdorms.services;

import com.uvtdorms.exception.AppException;
import com.uvtdorms.repository.IDormRepository;
import com.uvtdorms.repository.IRegisterRequestRepository;
import com.uvtdorms.repository.IRoomRepository;
import com.uvtdorms.repository.IStudentDetailsRepository;
import com.uvtdorms.repository.IUserRepository;
import com.uvtdorms.repository.dto.request.EditRoomDto;
import com.uvtdorms.repository.dto.request.RegisterStudentDto;
import com.uvtdorms.repository.dto.response.DormIdDto;
import com.uvtdorms.repository.dto.response.StudentDetailsDto;
import com.uvtdorms.repository.entity.Dorm;
import com.uvtdorms.repository.entity.RegisterRequest;
import com.uvtdorms.repository.entity.Room;
import com.uvtdorms.repository.entity.StudentDetails;
import com.uvtdorms.repository.entity.User;
import com.uvtdorms.repository.entity.enums.RegisterRequestStatus;
import com.uvtdorms.repository.entity.enums.Role;
import com.uvtdorms.services.interfaces.IStudentDetailsService;
import com.uvtdorms.utils.PasswordGenerator;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
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
        private final IRegisterRequestRepository registerRequestRepository;
        private final PasswordEncoder passwordEncoder;
        private final EmailService emailService;
        private final ModelMapper modelMapper;

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

        @SuppressWarnings("null")
        public void registerStudent(RegisterStudentDto registerStudentDto) throws AppException {
                verifyDataUniqueness(registerStudentDto);
                Room room = getSelectedRoomOnRegister(registerStudentDto.dormName(), registerStudentDto.roomNumber());
                String generatedPassword = PasswordGenerator.generateRandomPassword();

                User user = User.builder()
                                .firstName(registerStudentDto.firstName())
                                .lastName(registerStudentDto.lastName())
                                .email(registerStudentDto.email())
                                .phoneNumber(registerStudentDto.email())
                                .password(passwordEncoder.encode(generatedPassword))
                                .role(Role.STUDENT)
                                .isActive(false)
                                .build();

                userRepository.save(user);

                StudentDetails student = StudentDetails.builder()
                                .matriculationNumber(registerStudentDto.matriculationNumber())
                                .user(user)
                                .room(null)
                                .studentRegisterRequests(new ArrayList<>())
                                .build();

                studentDetailsRepository.save(student);

                RegisterRequest registerRequest = RegisterRequest.builder()
                                .student(student)
                                .room(room)
                                .createdOn(LocalDate.now())
                                .status(RegisterRequestStatus.RECEIVED)
                                .build();

                registerRequestRepository.save(registerRequest);

                student.getStudentRegisterRequests().add(registerRequest);
                studentDetailsRepository.save(student);

                room.getRoomRegisterRequests().add(registerRequest);
                roomRepository.save(room);

                user.setStudentDetails(student);
                userRepository.save(user);

                room.getStudentDetails().add(student);
                roomRepository.save(room);
                emailService.sendRegisterConfirm(user.getEmail(), user.getFirstName() + " " + user.getLastName(),
                                generatedPassword);
        }

        private void verifyDataUniqueness(final RegisterStudentDto registerStudentDto) {
                if (userRepository.getByEmail(registerStudentDto.email()).isPresent())
                        throw new AppException("Account already exists with this email.", HttpStatus.CONFLICT);

                if (studentDetailsRepository.findByMatriculationNumber(registerStudentDto.matriculationNumber())
                                .isPresent())
                        throw new AppException("Student already exists.", HttpStatus.CONFLICT);

                if (userRepository.findByPhoneNumber(registerStudentDto.phoneNumber()).isPresent())
                        throw new AppException("Phone number already in use.", HttpStatus.CONFLICT);
        }

        private Room getSelectedRoomOnRegister(final String dormName, final String roomNumber) {
                Dorm dorm = dormRepository.getByDormName(dormName);

                return roomRepository.findByDormAndRoomNumber(dorm, roomNumber)
                                .orElseThrow(() -> new AppException("Room not found", HttpStatus.NOT_FOUND));
        }

        public List<StudentDetailsDto> getAllStudentsFromDorm(Dorm dorm) {
                List<StudentDetails> students = studentDetailsRepository.findByRoomDorm(dorm);

                return students.stream()
                                .filter(student -> student.getUser().getIsActive())
                                .map(student -> modelMapper.map(student, StudentDetailsDto.class))
                                .collect(Collectors.toList());
        }

        public void updateRoomNumber(EditRoomDto editRoomDto) {
                StudentDetails student = studentDetailsRepository.findByUserEmail(editRoomDto.studentEmail())
                                .orElseThrow(() -> new AppException("Student not found", HttpStatus.NOT_FOUND));

                Room room = roomRepository
                                .findByDormAndRoomNumber(student.getRoom().getDorm(), editRoomDto.roomNumber())
                                .orElseThrow(() -> new AppException("Room not found", HttpStatus.NOT_FOUND));
                student.setRoom(room);
                studentDetailsRepository.save(student);

        }
}
