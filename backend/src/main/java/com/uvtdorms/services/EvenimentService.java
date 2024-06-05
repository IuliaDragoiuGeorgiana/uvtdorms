package com.uvtdorms.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.uvtdorms.exception.AppException;
import com.uvtdorms.repository.IEvenimentRepository;
import com.uvtdorms.repository.IUserRepository;
import com.uvtdorms.repository.dto.request.CreateEvenimentDto;
import com.uvtdorms.repository.dto.request.IdDto;
import com.uvtdorms.repository.dto.request.UpdateEvenimentDto;
import com.uvtdorms.repository.dto.response.EvenimentDetailsDto;
import com.uvtdorms.repository.entity.Dorm;
import com.uvtdorms.repository.entity.DormAdministratorDetails;
import com.uvtdorms.repository.entity.Eveniment;
import com.uvtdorms.repository.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EvenimentService {
    private final IEvenimentRepository evenimentRepository;
    private final IUserRepository userRepository;
    private final ModelMapper modelMapper;

    public List<EvenimentDetailsDto> getEvenimentsFromDorm(String userEmail) {
        User user = userRepository.getByEmail(userEmail).orElseThrow(
                () -> new AppException("User not found", HttpStatus.NOT_FOUND));

        Dorm dorm = null;

        if (user.getDormAdministratorDetails() != null) {
            dorm = user.getDormAdministratorDetails().getDorm();
        } else if (user.getStudentDetails() != null) {
            dorm = user.getStudentDetails().getRoom().getDorm();
        } else {
            throw new AppException("User is not a student or a dorm administrator", HttpStatus.BAD_REQUEST);
        }

        List<Eveniment> eveniments = evenimentRepository.findByDorm(dorm);
        List<EvenimentDetailsDto> evenimentDetailsDtos = new ArrayList<>();

        eveniments.sort((e1, e2) -> e2.getCreatedOnDate().compareTo(e1.getCreatedOnDate()));

        for (Eveniment eveniment : eveniments) {
            EvenimentDetailsDto evenimentDetailsDto = modelMapper.map(eveniment, EvenimentDetailsDto.class);
            if (eveniment.getAttendees() != null && eveniment.getAttendees().contains(user)) {
                evenimentDetailsDto.setIsUserAttending(true);
            } else {
                evenimentDetailsDto.setIsUserAttending(false);
            }
            evenimentDetailsDtos.add(evenimentDetailsDto);
        }

        return evenimentDetailsDtos;
    }

    public void createEveniment(String dormAdministratorEmail, CreateEvenimentDto createEvenimentDto) {
        User user = userRepository.getByEmail(dormAdministratorEmail).orElseThrow(
                () -> new AppException("User not found", HttpStatus.NOT_FOUND));

        DormAdministratorDetails dormAdministratorDetails = user.getDormAdministratorDetails();

        if (dormAdministratorDetails == null) {
            throw new AppException("User is not a dorm administrator", HttpStatus.BAD_REQUEST);
        }

        Dorm dorm = dormAdministratorDetails.getDorm();

        Eveniment eveniment = Eveniment.builder()
                .dorm(dorm)
                .title(createEvenimentDto.title())
                .description(createEvenimentDto.description())
                .canPeopleAttend(createEvenimentDto.canPeopleAttend())
                .startDate(createEvenimentDto.startDate().plusHours(3))
                .build();

        evenimentRepository.save(eveniment);
    }

    public void updateEveniment(String dormAdministratorEmail, UpdateEvenimentDto updateEvenimentDto) {
        User user = userRepository.getByEmail(dormAdministratorEmail).orElseThrow(
                () -> new AppException("User not found", HttpStatus.NOT_FOUND));

        DormAdministratorDetails dormAdministratorDetails = user.getDormAdministratorDetails();

        if (dormAdministratorDetails == null) {
            throw new AppException("User is not a dorm administrator", HttpStatus.BAD_REQUEST);
        }

        Dorm dorm = dormAdministratorDetails.getDorm();

        Eveniment eveniment = evenimentRepository.findById(UUID.fromString(updateEvenimentDto.id())).orElseThrow(
                () -> new AppException("Eveniment not found", HttpStatus.NOT_FOUND));

        if (!eveniment.getDorm().equals(dorm)) {
            throw new AppException("Eveniment does not belong to the dorm", HttpStatus.BAD_REQUEST);
        }

        eveniment.setTitle(updateEvenimentDto.title());
        eveniment.setDescription(updateEvenimentDto.description());
        eveniment.setCanPeopleAttend(updateEvenimentDto.canPeopleAttend());
        eveniment.setStartDate(updateEvenimentDto.startDate());

        if (eveniment.getCanPeopleAttend() == false) {
            if (eveniment.getAttendees() != null) {
                eveniment.getAttendees().clear();
            }
        }

        evenimentRepository.save(eveniment);
    }

    public void deleteEveniment(IdDto idDto) {
        Eveniment eveniment = evenimentRepository.findById(UUID.fromString(idDto.id())).orElseThrow(
                () -> new AppException("Eveniment not found", HttpStatus.NOT_FOUND));

        evenimentRepository.delete(eveniment);
    }

    public void attendToEveniment(String userEmail, IdDto idDto) {
        User user = userRepository.getByEmail(userEmail).orElseThrow(
                () -> new AppException("User not found", HttpStatus.NOT_FOUND));

        Eveniment eveniment = evenimentRepository.findById(UUID.fromString(idDto.id())).orElseThrow(
                () -> new AppException("Eveniment not found", HttpStatus.NOT_FOUND));

        if (eveniment.getCanPeopleAttend() == false) {
            throw new AppException("Eveniment does not allow people to attend", HttpStatus.BAD_REQUEST);
        }

        if (eveniment.getAttendees().contains(user)) {
            eveniment.getAttendees().remove(user);
        } else {
            eveniment.getAttendees().add(user);
        }

        evenimentRepository.save(eveniment);
    }
}
