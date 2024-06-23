package com.uvtdorms.services;

import com.uvtdorms.repository.dto.request.AddNewDormAdministratorDto;
import com.uvtdorms.repository.dto.request.UpdateDormAdministratorDto;
import com.uvtdorms.repository.dto.response.DetailedDormAdministratorDto;
import com.uvtdorms.repository.dto.response.DisplayDormAdministratorDetailsDto;
import com.uvtdorms.repository.dto.response.DormAdministratorDto;
import com.uvtdorms.repository.dto.response.DormIdDto;
import com.uvtdorms.repository.dto.response.StatisticsCountDto;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.uvtdorms.exception.AppException;
import com.uvtdorms.repository.IDormAdministratorDetailsRepository;
import com.uvtdorms.repository.IDormRepository;
import com.uvtdorms.repository.IUserRepository;
import com.uvtdorms.repository.entity.Dorm;
import com.uvtdorms.repository.entity.DormAdministratorDetails;
import com.uvtdorms.repository.entity.User;
import com.uvtdorms.repository.entity.enums.Role;
import com.uvtdorms.utils.PasswordGenerator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DormAdministratorService {
        private final IUserRepository userRepository;
        private final IDormAdministratorDetailsRepository dormAdministratorDetailsRepository;
        private final IDormRepository dormRepository;
        private final EmailService emailService;
        private final ModelMapper modelMapper;
        private final PasswordEncoder passwordEncoder;
        private final ResourceLoader resourceLoader;


        public Dorm getAdministratorDormByEmail(String email) {
                User user = userRepository.getByEmail(email)
                                .orElseThrow(() -> new AppException("User not found.", HttpStatus.NOT_FOUND));
                DormAdministratorDetails dormAdministratorDetails = dormAdministratorDetailsRepository
                                .findByAdministrator(user)
                                .orElseThrow(() -> new AppException("User not a dorm administrator.",
                                                HttpStatus.BAD_REQUEST));

                return dormAdministratorDetails.getDorm();
        }

        public DisplayDormAdministratorDetailsDto getDormAdministratorDetails(String email) {
                User user = userRepository.getByEmail(email)
                                .orElseThrow(() -> new AppException("User not found.", HttpStatus.NOT_FOUND));
                DormAdministratorDetails dormAdministratorDetails = dormAdministratorDetailsRepository
                                .findByAdministrator(user)
                                .orElseThrow(() -> new AppException("User not a dorm administrator.",
                                                HttpStatus.BAD_REQUEST));

                return modelMapper.map(dormAdministratorDetails, DisplayDormAdministratorDetailsDto.class);
        }

        public DormIdDto getAdministratedDormId(String dormAdministratorEmail) {
                Dorm administratedDorm = getAdministratorDormByEmail(dormAdministratorEmail);

                return DormIdDto.builder().id(administratedDorm.getDormId().toString()).build();
        }

        public List<DormAdministratorDto> getAvailableDormAdministrators() {
                List<DormAdministratorDetails> availableDormAdministrators = dormAdministratorDetailsRepository
                                .findByDorm(null);

                System.out.println(availableDormAdministrators.size());

                return availableDormAdministrators.stream()
                                .map(dormAdministratorDetails -> modelMapper.map(dormAdministratorDetails,
                                                DormAdministratorDto.class))
                                .toList();
        }

        public List<DetailedDormAdministratorDto> getDormAdministrators() {
                List<DormAdministratorDetails> dormAdministrators = dormAdministratorDetailsRepository.findAll();

                return dormAdministrators.stream()
                                .map(dormAdministratorDetails -> modelMapper.map(dormAdministratorDetails,
                                                DetailedDormAdministratorDto.class))
                                .toList();
        }
         private byte[] loadImage(String resourceName) throws IOException {
                Resource resource = resourceLoader.getResource("classpath:images/" + resourceName);
                try (InputStream inputStream = resource.getInputStream()) {
                        return IOUtils.toByteArray(inputStream);
                }
        }


        public void addNewDormAdministrator(AddNewDormAdministratorDto newDormAdministratorDto) {
                Boolean userAlreadyExists = userRepository.getByEmail(newDormAdministratorDto.email()).isPresent()
                                || userRepository.findByPhoneNumber(newDormAdministratorDto.phoneNumber()).isPresent();
                if (userAlreadyExists) {
                        throw new AppException("User already exists.", HttpStatus.BAD_REQUEST);
                }

                String password = PasswordGenerator.generateRandomPassword();
                String defaultProfilePicturePath = "user-profile.jpg";

                byte[] profilePicture = new byte[0];
                try {
                        profilePicture = loadImage(defaultProfilePicturePath);
                } catch (IOException e) {
                        e.printStackTrace();
                }

                User user = User.builder()
                                .firstName(newDormAdministratorDto.firstName())
                                .lastName(newDormAdministratorDto.lastName())
                                .email(newDormAdministratorDto.email())
                                .phoneNumber(newDormAdministratorDto.phoneNumber())
                                .password(passwordEncoder.encode(password))
                                .role(Role.ADMINISTRATOR)
                                .isActive(true)
                                .profilePicture(profilePicture)
                                .build();

                Dorm dorm = null;

                if (!newDormAdministratorDto.dormId().isEmpty()) {
                        dorm = dormRepository.getByDormId(UUID.fromString(newDormAdministratorDto.dormId()))
                                        .orElseThrow(
                                                        () -> new AppException("Dorm not found.",
                                                                        HttpStatus.NOT_FOUND));
                }

                DormAdministratorDetails dormAdministratorDetails = DormAdministratorDetails.builder()
                                .administrator(user)
                                .dorm(dorm)
                                .build();

                userRepository.save(user);
                dormAdministratorDetailsRepository.save(dormAdministratorDetails);

                if (dorm != null) {
                        dorm.setDormAdministratorDetails(dormAdministratorDetails);
                        dormRepository.save(dorm);
                        emailService.newDormAdministratorCreatedWithAssociatedDorm(user.getEmail(), user.getFullName(),
                                        dorm.getDormName(), password);
                } else {
                        emailService.newDormAdministratorCreated(user.getEmail(), user.getFullName(), password);
                }
        }

        public void updateAssociatedDorm(UpdateDormAdministratorDto updateDormAdministratorDto) {
                User user = userRepository.getByEmail(updateDormAdministratorDto.administratorEmail())
                                .orElseThrow(() -> new AppException("User not found.", HttpStatus.NOT_FOUND));

                Dorm dorm = null;

                if (!updateDormAdministratorDto.dormId().isEmpty()) {
                        dorm = dormRepository.getByDormId(UUID.fromString(updateDormAdministratorDto.dormId()))
                                        .orElseThrow(() -> new AppException("Dorm not found.", HttpStatus.NOT_FOUND));
                }

                DormAdministratorDetails dormAdministratorDetails = user.getDormAdministratorDetails();

                if (dormAdministratorDetails == null) {
                        throw new AppException("User is not a dorm administrator.", HttpStatus.BAD_REQUEST);
                }

                dormAdministratorDetails.setDorm(dorm);
                dormAdministratorDetailsRepository.save(dormAdministratorDetails);

                if (dorm != null) {
                        dorm.setDormAdministratorDetails(dormAdministratorDetails);
                        dormRepository.save(dorm);
                }
        }

        public void deleteDormAdministrator(String email) {
                User user = userRepository.getByEmail(email)
                                .orElseThrow(() -> new AppException("User not found.", HttpStatus.NOT_FOUND));
                DormAdministratorDetails dormAdministratorDetails = user.getDormAdministratorDetails();

                if (dormAdministratorDetails == null) {
                        throw new AppException("User is not a dorm administrator.", HttpStatus.BAD_REQUEST);
                }

                Dorm dorm = dormAdministratorDetails.getDorm();

                if (dorm != null) {
                        throw new AppException("Dorm administrator is associated with a dorm.", HttpStatus.BAD_REQUEST);
                }

                dormAdministratorDetails.getAdministrator().setDormAdministratorDetails(null);
                dormAdministratorDetails.setAdministrator(null);

                dormAdministratorDetailsRepository.delete(dormAdministratorDetails);
                userRepository.delete(user);
        }

        public StatisticsCountDto getDormAdministratorCount() {

                StatisticsCountDto numberOfDormAdministrtor = new StatisticsCountDto();
                numberOfDormAdministrtor.setCount(dormAdministratorDetailsRepository.findAll().size());
                return numberOfDormAdministrtor;
        }

        public boolean hasDormAdministratorAssociatedDorm(String email) {
                User user = userRepository.getByEmail(email)
                                .orElseThrow(() -> new AppException("User not found.", HttpStatus.NOT_FOUND));
                DormAdministratorDetails dormAdministratorDetails = user.getDormAdministratorDetails();

                if (dormAdministratorDetails == null) {
                        throw new AppException("User is not a dorm administrator.", HttpStatus.BAD_REQUEST);
                }

                return dormAdministratorDetails.getDorm() != null;
        }
}
