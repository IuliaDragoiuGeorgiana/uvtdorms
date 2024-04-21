package com.uvtdorms.utils;

import com.uvtdorms.repository.dto.response.DisplayStudentDetailsDto;
import com.uvtdorms.repository.dto.response.DryerDto;
import com.uvtdorms.repository.dto.response.EmailDto;
import com.uvtdorms.repository.dto.response.ListedRegisterRequestDto;
import com.uvtdorms.repository.dto.response.RegisterRequestDto;
import com.uvtdorms.repository.dto.response.StudentDetailsDto;
import com.uvtdorms.repository.dto.response.WashingMachineDto;
import com.uvtdorms.repository.entity.Dryer;
import com.uvtdorms.repository.entity.RegisterRequest;
import com.uvtdorms.repository.entity.StudentDetails;
import com.uvtdorms.repository.entity.User;
import com.uvtdorms.repository.entity.WashingMachine;
import com.uvtdorms.repository.entity.enums.StatusMachine;
import org.modelmapper.PropertyMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.hibernate.mapping.Property;
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

        modelMapper.addMappings(new PropertyMap<User, EmailDto>() {
            @Override
            protected void configure() {
                map(source.getEmail(), destination.getEmail());
            }
        });

        modelMapper.addMappings(new PropertyMap<RegisterRequest, RegisterRequestDto>() {
            @Override
            protected void configure() {
                map(source.getStudent().getUser().getFirstName(), destination.getFirstName());
                map(source.getStudent().getUser().getLastName(), destination.getLastName());
                map(source.getStudent().getUser().getEmail(), destination.getEmail());
                map(source.getStudent().getUser().getPhoneNumber(), destination.getPhoneNumber());
                map(source.getRoom().getDorm().getDormName(), destination.getDormName());
                map(source.getRoom().getRoomNumber(), destination.getRoomNumber());
                map(source.getStudent().getMatriculationNumber(), destination.getMatriculationNumber());
                map(source.getStatus(), destination.getStatus());
                map(source.getCreatedOn(), destination.getCreatedOn());
            }
        });

        modelMapper.addMappings(new PropertyMap<StudentDetails, StudentDetailsDto>() {
            @Override
            protected void configure() {
                map(source.getUser().getFirstName(), destination.getFirstName());
                map(source.getUser().getLastName(), destination.getLastName());
                map(source.getUser().getEmail(), destination.getEmail());
                map(source.getUser().getPhoneNumber(), destination.getPhoneNumber());
                map(source.getRoom().getDorm().getDormName(), destination.getDormName());
                map(source.getRoom().getRoomNumber(), destination.getRoomNumber());
                map(source.getMatriculationNumber(), destination.getMatriculationNumber());
            }
        });

        modelMapper.addMappings(new PropertyMap<StudentDetails,DisplayStudentDetailsDto>(){
            @Override
            protected void configure(){
                map(source.getUser().getEmail(), destination.getEmail());
                map(source.getUser().getPhoneNumber(), destination.getPhoneNumber());
                map(source.getRoom().getDorm().getDormName(), destination.getDormName());
                map(source.getRoom().getRoomNumber(), destination.getRoomNumber());
                map(source.getMatriculationNumber(), destination.getMatriculationNumber());
            }
        });

        modelMapper.addMappings(new PropertyMap<RegisterRequest, ListedRegisterRequestDto>() {
            @Override
            protected void configure() {
                map(source.getCreatedOn(), destination.getCreatedOn());
                map(source.getRoom().getDorm().getDormName(), destination.getDormitoryName());
                map(source.getRoom().getRoomNumber(), destination.getRoomNumber());
                map(source.getRoom().getDorm().getDormAdministratorDetails().getAdministrator().getEmail(),
                        destination.getAdministratorContact());
                map(source.getStatus(), destination.getStatus());
            }
        });

        return modelMapper;
    }
}
