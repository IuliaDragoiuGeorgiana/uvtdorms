package com.uvtdorms.utils;

import com.uvtdorms.repository.dto.response.DryerDto;
import com.uvtdorms.repository.dto.response.UserDto;
import com.uvtdorms.repository.dto.response.WashingMachineDto;
import com.uvtdorms.repository.entity.Dryer;
import com.uvtdorms.repository.entity.User;
import com.uvtdorms.repository.entity.WashingMachine;
import com.uvtdorms.repository.entity.enums.StatusMachine;
import org.modelmapper.PropertyMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.modelmapper.Converter;
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
                map(source.getMachineNumber(), destination.getName());

                Converter<StatusMachine, Boolean> statusConverter = new Converter<StatusMachine, Boolean>() {
                    public Boolean convert(MappingContext<StatusMachine, Boolean> context) {
                        return context.getSource() != null && context.getSource().equals(StatusMachine.FUNCTIONAL);
                    }
                };
                using(statusConverter).map(source.getStatus(), destination.getIsAvailable());
            }
        });

        modelMapper.addMappings(new PropertyMap<Dryer, DryerDto>() {
            @Override
            protected void configure() {
                using(ctx -> ((UUID) ctx.getSource()).toString()).map(source.getDryerId(), destination.getId());
                map(source.getDryerNumber(), destination.getName());

                Converter<StatusMachine, Boolean> statusConverter = new Converter<StatusMachine, Boolean>() {
                    public Boolean convert(MappingContext<StatusMachine, Boolean> context) {
                        return context.getSource() != null && context.getSource().equals(StatusMachine.FUNCTIONAL);
                    }
                };
                using(statusConverter).map(source.getStatus(), destination.getIsAvailable());
            }
        });

        modelMapper.addMappings(new PropertyMap<User, UserDto>() {
            @Override
            protected void configure() {
                map(source.getEmail(), destination.getEmail());
            }
        });

        return modelMapper;
    }
}
