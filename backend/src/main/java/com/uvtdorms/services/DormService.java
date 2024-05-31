package com.uvtdorms.services;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.uvtdorms.repository.IDormAdministratorDetailsRepository;
import com.uvtdorms.repository.IDormRepository;
import com.uvtdorms.repository.IUserRepository;
import com.uvtdorms.repository.dto.response.DormDto;
import com.uvtdorms.repository.dto.response.DormsNamesDto;
import com.uvtdorms.repository.entity.Dorm;
import com.uvtdorms.repository.entity.DormAdministratorDetails;
import com.uvtdorms.repository.entity.User;

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
}
