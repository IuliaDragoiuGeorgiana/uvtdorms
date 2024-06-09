package com.uvtdorms.utils;

import com.uvtdorms.repository.*;
import com.uvtdorms.repository.entity.*;
import com.uvtdorms.repository.entity.enums.RegisterRequestStatus;
import com.uvtdorms.repository.entity.enums.Role;
import com.uvtdorms.repository.entity.enums.StatusMachine;
import com.uvtdorms.repository.entity.enums.StatusTicket;

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
        private final IEvenimentRepository evenimentRepository;
        private final ITicketRepository ticketRepository;

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

        private DormAdministratorDetails createDormAdministrator(String firstName, String lastName, String email,
                        String phoneNumber,
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

                if (dorm != null)
                        dorm.setDormAdministratorDetails(dormAdministratorDetails);

                return dormAdministratorDetails;
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

        public Eveniment createEveniment(String title, String description, Dorm dorm, DormAdministratorDetails creator,
                        LocalDateTime eventDate) {
                Eveniment eveniment = Eveniment.builder().title(title).description(description).dorm(dorm)
                                .createdBy(creator).startDate(eventDate).canPeopleAttend(true).build();

                evenimentRepository.save(eveniment);

                return eveniment;
        }

        public void attendUserToEveniment(Eveniment eveniment, User user) {
                if (eveniment.getAttendees() == null)
                        eveniment.setAttendees(new ArrayList<>());
                eveniment.getAttendees().add(user);
                evenimentRepository.save(eveniment);
        }
        
        public Ticket createTicket(String title, String description, Dorm dorm, StudentDetails student) {
                Ticket ticket = Ticket.builder()
                                .title(title)
                                .description(description)
                                .statusTicket(StatusTicket.OPEN)
                                .creationDate(LocalDateTime.now())
                                .alreadyAnuncement(false)
                                .dorm(dorm)
                                .student(student)
                                .build();
                
                ticketRepository.save(ticket);

                return ticket;
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

                DormAdministratorDetails dorm13Admin = createDormAdministrator("Tom", "Hanks", "tom.hanks@e-uvt.ro",
                                "0712345678", "hello", dorm13,
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
                createLaundryAppointment(LocalDateTime.now().minusDays(1).withHour(8).withMinute(0).withSecond(0),
                                taylorSwift,
                                dorm13Machine1);
                createLaundryAppointment(LocalDateTime.now().plusDays(1).withHour(10).withMinute(0).withSecond(0),
                                iuliaDragoiu,
                                dorm13Machine1);
                createLaundryAppointment(LocalDateTime.now().withHour(10).withMinute(0).withSecond(0), emmaWatson,
                                dorm13Machine1);

                createApplicationAdministrator("Adam", "Sandler", "adam.sandler@yahoo.com", "0789189456", "hello",
                                "user-profile.jpg");

                createDorm("C9", "Street2");
                createDormAdministrator("Hayao", "Miyazaki", "hayao.miyazaki@e-uvt.ro", "0782132131", "hello",
                                null,
                                "user-profile.jpg");

                createEveniment("Movie Marathon: Sci-Fi Extravaganza!",
                                "<p>Calling all sci-fi enthusiasts! Get ready for a night of epic proportions with our Movie Marathon: Sci-Fi Extravaganza! We'll be screening a selection of classic and new sci-fi films, with popcorn and snacks provided. So grab your friends, put on your spacesuits (optional!), and join us for an unforgettable journey through the cosmos!</p><p>Schedule:<br>- 7:00 PM: Arrival and snacks<br>- 7:30 PM: Movie 1 (title to be announced)<br>- 9:30 PM: Intermission with games and trivia<br>- 10:00 PM: Movie 2 (title to be announced)</p>",
                                dorm13, dorm13.getDormAdministratorDetails(), LocalDateTime.now().plusDays(2));

                createEveniment("Volunteer Bake Sale: Supporting the Animal Shelter",
                                "<p>Calling all bakers and dessert enthusiasts! We're hosting a Volunteer Bake Sale to raise funds for the local animal shelter. Put your baking skills to the test and donate your delicious creations (or simply come by to indulge!). All proceeds will go towards providing care and comfort for homeless animals.</p><p>How to Participate:<br>- Sign up to donate baked goods by contacting [email protected]<br>- Donations can be dropped off at the event location on the day of the sale.</p>",
                                dorm13, dorm13.getDormAdministratorDetails(), LocalDateTime.now().plusMonths(2));

                createEveniment("Plant Swap Party: Grow Your Collection!",
                                "<p>Calling all plant lovers! Join us for a fun and sustainable Plant Swap Party. Bring a healthy, unwanted plant from your collection and swap it for something new! It's a great way to expand your plant family, declutter your space, and meet other plant enthusiasts. We'll also have resources and tips on plant care available.</p><p>What to Bring:<br>- A healthy, unwanted plant (pots included)<br>- Your enthusiasm for all things green!</p>",
                                dorm13, dorm13.getDormAdministratorDetails(), LocalDateTime.now().plusWeeks(3));
                createTicket("Test ticket", "Detailed description.", dorm13, iuliaDragoiu);
        }

        private byte[] loadImage(String resourceName) throws IOException {
                Resource resource = resourceLoader.getResource("classpath:images/" + resourceName);
                try (InputStream inputStream = resource.getInputStream()) {
                        return IOUtils.toByteArray(inputStream);
                }
        }
}
