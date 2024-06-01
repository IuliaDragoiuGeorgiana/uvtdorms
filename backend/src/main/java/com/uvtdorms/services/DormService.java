package com.uvtdorms.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.uvtdorms.exception.AppException;
import com.uvtdorms.repository.IDormAdministratorDetailsRepository;
import com.uvtdorms.repository.IDormRepository;
import com.uvtdorms.repository.IUserRepository;
import com.uvtdorms.repository.dto.request.UpdateDormAdministratorDto;
import com.uvtdorms.repository.dto.response.DormDto;
import com.uvtdorms.repository.dto.response.DormsNamesDto;
import com.uvtdorms.repository.entity.Dorm;
import com.uvtdorms.repository.entity.DormAdministratorDetails;
import com.uvtdorms.repository.entity.User;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DormService {
    private final IDormRepository dormRepository;
    private final IUserRepository userRepository;
    private final IDormAdministratorDetailsRepository dormAdministratorDetailsRepository;
    private final ModelMapper mapper;

    public DormsNamesDto getDormsNames() {
        List<String> dormNames = new ArrayList<>();
        List<Dorm> dorms = dormRepository.findAll();

        dorms.stream().forEach((dorm) -> {
            dormNames.add(dorm.getDormName());
        });

        return new DormsNamesDto(dormNames);
    }

    public List<DormDto> getAllDorms() {
        List<Dorm> dorms = dormRepository.findAll();

        return dorms.stream().map(dorm -> mapper.map(dorm, DormDto.class)).toList();
    }

    public void addDorm(DormDto dormDto) {
        DormAdministratorDetails dormAdministratorDetails = null;

        User user = userRepository.getByEmail(dormDto.getAdministratorEmail()).orElse(null);

        if (user != null) {
            System.out.println("User found");
            dormAdministratorDetails = user.getDormAdministratorDetails();
        }

        if (dormAdministratorDetails == null) {
            System.out.println("User not found");
        }

        Dorm dorm = Dorm.builder()
                .dormName(dormDto.getName())
                .address(dormDto.getAddress())
                .dormAdministratorDetails(dormAdministratorDetails)
                .build();

        dormRepository.save(dorm);

        if (dormAdministratorDetails != null) {
            dormAdministratorDetails.setDorm(dorm);
            dormAdministratorDetailsRepository.save(dormAdministratorDetails);
        }

    }

    public void updateDormAdministrator(UpdateDormAdministratorDto updateDormAdministratorDto) {

        Dorm dorm = dormRepository.getByDormId(UUID.fromString(updateDormAdministratorDto.dormId()))
                .orElseThrow(() -> new AppException("Invalid dorm ID", HttpStatus.BAD_REQUEST));
        User user = userRepository.getByEmail(updateDormAdministratorDto.administratorEmail())
                .orElse(null);

        DormAdministratorDetails dormAdministratorDetails = null;

        if (user != null) {
            dormAdministratorDetails = user.getDormAdministratorDetails();
        }

        if (dorm.getDormAdministratorDetails() != null) {
            dorm.getDormAdministratorDetails().setDorm(null);
        }

        if (dormAdministratorDetails != null) {
            dormAdministratorDetails.setDorm(dorm);
        }

        dorm.setDormAdministratorDetails(dormAdministratorDetails);
        dormRepository.save(dorm);
    }

    @Transactional
    public void deleteDorm(String dormId) {
        Dorm dorm = dormRepository.getByDormId(UUID.fromString(dormId))
                .orElseThrow(() -> new AppException("Invalid dorm ID", HttpStatus.BAD_REQUEST));

        if (dorm.getRooms().stream().anyMatch(room -> room.getStudentDetails().size() > 0)) {
            throw new AppException("Dorm has students", HttpStatus.BAD_REQUEST);
        }

        dormRepository.delete(dorm);
    }
}
