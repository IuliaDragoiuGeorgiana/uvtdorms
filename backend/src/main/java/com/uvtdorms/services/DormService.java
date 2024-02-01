package com.uvtdorms.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.uvtdorms.repository.IDormRepository;
import com.uvtdorms.repository.dto.response.DormsNamesDto;
import com.uvtdorms.repository.entity.Dorm;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DormService {
    private final IDormRepository dormRepository;

    public DormsNamesDto getDormsNames() {
        List<String> dormNames = new ArrayList<>();
        List<Dorm> dorms = dormRepository.findAll();

        dorms.stream().forEach((dorm) -> {
            dormNames.add(dorm.getDormName());
        });

        return new DormsNamesDto(dormNames);
    }
}
