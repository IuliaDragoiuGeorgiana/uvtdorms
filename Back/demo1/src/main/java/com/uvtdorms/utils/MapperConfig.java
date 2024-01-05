package com.uvtdorms.utils;

import com.uvtdorms.repository.dto.response.WashingMachineDto;
import com.uvtdorms.repository.entity.WashingMachine;
import com.uvtdorms.repository.entity.enums.StatusMachine;
import org.hibernate.annotations.Comment;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.modelmapper.ModelMapper;

import java.util.UUID;

@Component
public class MapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.addMappings(new PropertyMap<WashingMachine, WashingMachineDto>() {
            @Override
            protected void configure() {
                using(ctx -> ((UUID) ctx.getSource()).toString()).map(source.getMachineId(), destination.getId());
                map().setName(source.getMachineNumber());
                map().setIsAvailable(source.getStatus() == StatusMachine.FUNCTIONAL);            }
        });

        return modelMapper;
    }
}
