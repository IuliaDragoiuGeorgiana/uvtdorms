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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

    private List<String> dormsNamesList = Arrays.asList("C13", "C12");
    private List<String> roomsNamesList = Arrays.asList("127", "128");

    private void initializeDorms() {
        List<String> adressesNamesList = Arrays.asList("F.C. Ripesnsia", "Studentilor");
        for (int i = 0; i < dormsNamesList.size(); i++) {
            Dorm dorm = new Dorm();
            dorm.setDormName(dormsNamesList.get(i));
            dorm.setAddress(adressesNamesList.get(i));
            dormRepository.save(dorm);
        }
    }

    private void initializeRooms() {
        for (int i = 0; i < roomsNamesList.size(); i++) {
            Dorm dorm = dormRepository.getByDormName(dormsNamesList.get(i));
            Room room = new Room();
            room.setRoomNumber(roomsNamesList.get(i));
            room.setDorm(dorm);
            roomRepository.save(room);
        }
    }

    @SuppressWarnings("null")
    private void initializeStudents() {
        Optional<Room> room = roomRepository.getRoomByRoomNumber(roomsNamesList.get(0));
        if (room.isPresent()) {
            User user = User.builder()
                    .firstName("Iulia")
                    .lastName("Dragoiu")
                    .email("iulia.dragoiu02@e-uvt.ro")
                    .phoneNumber("0729616799")
                    .password(passwordEncoder.encode("iuliad"))
                    .role(Role.STUDENT)
                    .isActive(true)
                    .build();

            userRepository.save(user);
            StudentDetails student = new StudentDetails("I3183", user, room.get());
            studentDetailsRepository.save(student);
        }
    }

    @SuppressWarnings("null")
    private void initializeDormsAdministrators() {
        Dorm dorm = dormRepository.getByDormName(dormsNamesList.get(0));

        User user = User.builder()
                .firstName("Iulia123")
                .lastName("Dragoiu123")
                .email("iulia.dragoiu02123@e-uvt.ro")
                .phoneNumber("07295540479")
                .password(passwordEncoder.encode("iuliad123"))
                .role(Role.ADMINISTRATOR)
                .isActive(true)
                .build();

        userRepository.save(user);
        DormAdministratorDetails administrator = DormAdministratorDetails.builder().administrator(user).dorm(dorm)
                .build();

        dormAdministratorDetailsRepository.save(administrator);

    }

    private void initializeMachinesAndDryers() {
        List<String> washingMachinesNames = Arrays.asList("Machine1", "Machine2");
        List<String> dryersNames = Arrays.asList("Dryer1", "Dryer2");
        for (String dormsName : dormsNamesList) {
            Dorm dorm = dormRepository.getByDormName(dormsName);
            for (String washingMachineName : washingMachinesNames) {
                WashingMachine washingMachine = new WashingMachine(washingMachineName, dorm, StatusMachine.FUNCTIONAL);
                washingMachineRepository.save(washingMachine);
            }
            for (String dryerName : dryersNames) {
                Dryer dryer = new Dryer(dryerName, dorm, StatusMachine.FUNCTIONAL);
                dryerRepository.save(dryer);
            }
        }
        Dorm dorm = dormRepository.getByDormName("C12");
        WashingMachine washingMachine = new WashingMachine("Machine3", dorm, StatusMachine.FUNCTIONAL);
        washingMachineRepository.save(washingMachine);
    }

    private void initializeAppointments() {
        Optional<User> user = userRepository.getByEmail("iulia.dragoiu02@e-uvt.ro");
        if (user.isEmpty())
            return;

        Optional<StudentDetails> student = studentDetailsRepository.findByUser(user.get());
        if (student.isEmpty())
            return;

        Dorm dorm = dormRepository.getByDormName(dormsNamesList.get(0));

        List<WashingMachine> washingMachines = washingMachineRepository.findByDorm(dorm);
        List<Dryer> dryers = dryerRepository.findByDorm(dorm);

        for (int i = 0; i < washingMachines.size() && i < dryers.size(); i++) {
            LocalDateTime intervalBeginDate = LocalDate.now().plusDays(1).atTime(8, 0);
            LaundryAppointment laundryAppointment = new LaundryAppointment(intervalBeginDate, student.get(),
                    washingMachines.get(i), dryers.get(i));
            laundryAppointmentRepository.save(laundryAppointment);
        }
    }

    @SuppressWarnings("null")
    private Dorm createDorm(String dormName, String address) {
        Dorm dorm = Dorm.builder()
                .dormName(dormName)
                .address(address)
                .build();

        dormRepository.save(dorm);

        return dorm;
    }

    @SuppressWarnings("null")
    private Room createRoom(String roomNumber, Dorm dorm) {
        Room room = Room.builder().dorm(dorm).roomNumber(roomNumber).build();

        roomRepository.save(room);

        return room;
    }

    @SuppressWarnings("null")
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

    @SuppressWarnings("null")
    private void createRegisterRequest(String firstName, String lastName, String email, String phoneNumber,
            String password, Room room, String matriculationNumber,String profilePictureFileName) {
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
                .role(Role.STUDENT)
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

    @SuppressWarnings("null")
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

        StudentDetails student = StudentDetails.builder().room(room).matriculationNumber(matriculationNumber).user(user)
                .build();

        studentDetailsRepository.save(student);

        user.setStudentDetails(student);
        userRepository.save(user);

        return student;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        Dorm dorm1 = createDorm("D13", "Street1");
        createDormAdministrator("Tom", "Hanks", "tom.hanks@e-uvt.ro", "0712345678", "hello", dorm1, "user-profile.jpg");
        Room room1 = createRoom("1", dorm1);
        // Room room2 = createRoom("2", dorm1);
        createStudent("Taylor", "Swift", "taylor.swift@e-uvt.ro", "0765891234", "hello", room1, "I2345","user-profile.jpg");
        createRegisterRequest("Vin", "Diesel", "vin.diesel@e-uvt.ro", "0789123456", "hello", room1, "I1234","user-profile.jpg");
        initializeDorms();
        initializeRooms();
        initializeStudents();
        initializeDormsAdministrators();
        initializeMachinesAndDryers();
        initializeAppointments();
    }

    private byte[] loadImage(String resourceName) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:images/" + resourceName);
        try (InputStream inputStream = resource.getInputStream()) {
            return IOUtils.toByteArray(inputStream);
        }
    }
}
