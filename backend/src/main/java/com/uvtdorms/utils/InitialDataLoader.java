package com.uvtdorms.utils;

import com.uvtdorms.repository.*;
import com.uvtdorms.repository.entity.*;
import com.uvtdorms.repository.entity.enums.RegisterRequestStatus;
import com.uvtdorms.repository.entity.enums.Role;
import com.uvtdorms.repository.entity.enums.StatusMachine;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class InitialDataLoader implements CommandLineRunner {
        private final ILaundryAppointmentRepository laundryAppointmentRepository;
        private final IDormRepository dormRepository;
        private final IDryerRepository dryerRepository;
        private final IRoomRepository roomRepository;
        private final IStudentDetailsRepository studentDetailsRepository;
        private final IUserRepository userRepository;
        private final IWashingMachineRepository washingMachineRepository;
        private final IDormAdministratorDetailsRepository dormAdministratorDetailsRepository;
        private final PasswordEncoder passwordEncoder;
        private final IRegisterRequestRepository registerRequestRepository;
        private final ResourceLoader resourceLoader;

        private Dorm createDorm(String dormName, String address) {
                Dorm dorm = Dorm.builder()
                                .dormName(dormName)
                                .address(address)
                                .build();

                dormRepository.save(dorm);

                return dorm;
        }

        private WashingMachine createWashingMachine(String washingMachineName, Dorm dorm, StatusMachine statusMachine,
                        Dryer associatedDryer) {
                WashingMachine washingMachine = WashingMachine.builder().machineNumber(washingMachineName).dorm(dorm)
                                .status(statusMachine).associatedDryer(associatedDryer).build();
                washingMachineRepository.save(washingMachine);

                return washingMachine;
        }

        private Dryer createDryer(String dryerName, Dorm dorm, StatusMachine statusMachine,
                        WashingMachine associatedWashingMachine) {
                Dryer dryer = Dryer.builder().dryerNumber(dryerName).dorm(dorm).status(statusMachine)
                                .associatedWashingMachine(associatedWashingMachine).build();
                dryerRepository.save(dryer);

                return dryer;
        }

        private Room createRoom(String roomNumber, Dorm dorm) {
                Room room = Room.builder().dorm(dorm).roomNumber(roomNumber).build();

                roomRepository.save(room);

                return room;
        }

        private User createDormAdministrator(String firstName, String lastName, String email, String phoneNumber,
                        String password, Dorm dorm, String profilePictureFileName) {
                byte[] profilePicture = new byte[0];
                try {
                        profilePicture = loadImage(profilePictureFileName);
                } catch (IOException e) {
                        e.printStackTrace();
                }
                User admin = User.builder()
                                .firstName(firstName)
                                .lastName(lastName)
                                .email(email)
                                .phoneNumber(phoneNumber)
                                .password(passwordEncoder.encode(password))
                                .role(Role.ADMINISTRATOR)
                                .isActive(true)
                                .profilePicture(profilePicture)
                                .build();

                userRepository.save(admin);

                DormAdministratorDetails dormAdministratorDetails = DormAdministratorDetails.builder()
                                .administrator(admin)
                                .dorm(dorm)
                                .build();

                dormAdministratorDetailsRepository.save(dormAdministratorDetails);

                return admin;
        }

        private void createRegisterRequest(String firstName, String lastName, String email, String phoneNumber,
                        String password, Room room, String matriculationNumber, String profilePictureFileName) {
                byte[] profilePicture = new byte[0];
                try {
                        profilePicture = loadImage(profilePictureFileName);
                } catch (IOException e) {
                        e.printStackTrace();
                }

                User user = User.builder()
                                .firstName(firstName)
                                .lastName(lastName)
                                .email(email)
                                .phoneNumber(phoneNumber)
                                .password(passwordEncoder.encode(password))
                                .isActive(false)
                                .role(Role.INACTIV_STUDENT)
                                .profilePicture(profilePicture)
                                .build();

                userRepository.save(user);

                StudentDetails student = StudentDetails.builder().studentRegisterRequests(new ArrayList<>()).room(null)
                                .matriculationNumber(matriculationNumber).user(user).build();

                studentDetailsRepository.save(student);

                user.setStudentDetails(student);
                userRepository.save(user);

                RegisterRequest registerRequest = RegisterRequest.builder().createdOn(LocalDate.now()).room(room)
                                .student(student).status(RegisterRequestStatus.RECEIVED).build();

                registerRequestRepository.save(registerRequest);

                student.getStudentRegisterRequests().add(registerRequest);
                studentDetailsRepository.save(student);
        }

        private StudentDetails createStudent(String firstName, String lastName, String email, String phoneNumber,
                        String password, Room room, String matriculationNumber, String profilePictureFileName) {
                byte[] profilePicture = new byte[0];
                try {
                        profilePicture = loadImage(profilePictureFileName);
                } catch (IOException e) {
                        e.printStackTrace();
                }

                User user = User.builder()
                                .firstName(firstName)
                                .lastName(lastName)
                                .email(email)
                                .phoneNumber(phoneNumber)
                                .password(passwordEncoder.encode(password))
                                .isActive(true)
                                .role(Role.STUDENT)
                                .profilePicture(profilePicture)
                                .build();

                userRepository.save(user);

                StudentDetails student = StudentDetails.builder().room(room).matriculationNumber(matriculationNumber)
                                .user(user)
                                .build();

                studentDetailsRepository.save(student);

                user.setStudentDetails(student);
                userRepository.save(user);

                return student;
        }

        private User createApplicationAdministrator(String firstName, String lastName, String email, String phoneNumber,
                        String password, String profilePictureFileName) {

                byte[] profilePicture = new byte[0];
                try {
                        profilePicture = loadImage(profilePictureFileName);
                } catch (IOException e) {
                        e.printStackTrace();
                }
                User user = User.builder()
                                .firstName(firstName)
                                .lastName(lastName)
                                .email(email)
                                .phoneNumber(phoneNumber)
                                .password(passwordEncoder.encode(password))
                                .isActive(true)
                                .profilePicture(profilePicture)
                                .role(Role.APPLICATION_ADMINISTRATOR)
                                .build();

                userRepository.save(user);

                return user;
        }

        public LaundryAppointment createLaundryAppointment(LocalDateTime intervalBeginDate, StudentDetails student,
                        WashingMachine washingMachine) {
                LaundryAppointment laundryAppointment = new LaundryAppointment(intervalBeginDate, student,
                                washingMachine,
                                washingMachine.getAssociatedDryer());

                laundryAppointmentRepository.save(laundryAppointment);

                return laundryAppointment;
        }

        @Override
        @Transactional
        public void run(String... args) throws Exception {
                Dorm dorm13 = createDorm("D13", "Street1");

                WashingMachine dorm13Machine1 = createWashingMachine("Machine1", dorm13, StatusMachine.FUNCTIONAL,
                                null);
                WashingMachine dorm13Machine2 = createWashingMachine("Machine2", dorm13, StatusMachine.FUNCTIONAL,
                                null);
                WashingMachine dorm13Machine3 = createWashingMachine("Machine3", dorm13, StatusMachine.FUNCTIONAL,
                                null);

                Dryer dorm13Dryer1 = createDryer("Dryer1", dorm13, StatusMachine.FUNCTIONAL, dorm13Machine1);
                Dryer dorm13Dryer2 = createDryer("Dryer2", dorm13, StatusMachine.FUNCTIONAL, dorm13Machine2);
                Dryer dorm13Dryer3 = createDryer("Dryer3", dorm13, StatusMachine.FUNCTIONAL, null);

                dorm13Machine1.setDryer(dorm13Dryer1);
                dorm13Machine2.setDryer(dorm13Dryer2);

                createDormAdministrator("Tom", "Hanks", "tom.hanks@e-uvt.ro", "0712345678", "hello", dorm13,
                                "user-profile.jpg");

                Room room1 = createRoom("1", dorm13);
                Room room2 = createRoom("2", dorm13);

                StudentDetails taylorSwift = createStudent("Taylor", "Swift", "taylor.swift@e-uvt.ro", "0765891234",
                                "hello",
                                room1, "I2345",
                                "user-profile.jpg");
                StudentDetails emmaWatson = createStudent("Emma", "Watson", "emma.watson@e-uvt.ro", "0763213213",
                                "hello",
                                room2, "I1234",
                                "user-profile.jpg");
                StudentDetails iuliaDragoiu = createStudent("Iulia", "Dragoiu",
                                "iulia.dragoiu02@e-uvt.ro",
                                "0747319234",
                                "hello",
                                room2, "I1239",
                                "user-profile.jpg");

                createRegisterRequest("Vin", "Diesel", "vin.diesel@e-uvt.ro", "0789123456", "hello", room1, "I1235",
                                "user-profile.jpg");
                createRegisterRequest("IuliBuli", "Geo", "iuliadragoiu2@gmail.com", "0789133456", "hello", room2,
                                "I1834",
                                "user-profile.jpg");

                createLaundryAppointment(LocalDateTime.now().plusDays(1).withHour(8).withMinute(0).withSecond(0),
                                taylorSwift,
                                dorm13Machine1);
                createLaundryAppointment(LocalDateTime.now().plusDays(1).withHour(10).withMinute(0).withSecond(0),
                                iuliaDragoiu,
                                dorm13Machine1);
                createLaundryAppointment(LocalDateTime.now().withHour(10).withMinute(0).withSecond(0), emmaWatson,
                                dorm13Machine1);

                createApplicationAdministrator("Adam", "Sandler", "adam.sandler@e-uvt.ro", "0789189456", "hello",
                                "user-profile.jpg");

                createDorm("C9", "Street2");
                createDormAdministrator("Hayao", "Miyazaki", "hayao.miyazaki@e-uvt.ro", "0782132131", "hello",
                                null,
                                "user-profile.jpg");
        }

        private byte[] loadImage(String resourceName) throws IOException {
                Resource resource = resourceLoader.getResource("classpath:images/" + resourceName);
                try (InputStream inputStream = resource.getInputStream()) {
                        return IOUtils.toByteArray(inputStream);
                }
        }
}
