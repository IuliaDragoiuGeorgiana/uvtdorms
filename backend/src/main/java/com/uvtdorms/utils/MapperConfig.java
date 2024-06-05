package com.uvtdorms.utils;

import com.uvtdorms.repository.dto.response.AvailableDormDto;
import com.uvtdorms.repository.dto.response.AvailableDryerDto;
import com.uvtdorms.repository.dto.response.AvailableWashingMachineDto;
import com.uvtdorms.repository.dto.response.DetailedDormAdministratorDto;
import com.uvtdorms.repository.dto.response.DetailedRoomDto;
import com.uvtdorms.repository.dto.response.DisplayDormAdministratorDetailsDto;
import com.uvtdorms.repository.dto.response.DisplayStudentDetailsDto;
import com.uvtdorms.repository.dto.response.DormAdministratorDto;
import com.uvtdorms.repository.dto.response.DormDto;
import com.uvtdorms.repository.dto.response.DryerDto;
import com.uvtdorms.repository.dto.response.EmailDto;
import com.uvtdorms.repository.dto.response.EvenimentDetailsDto;
import com.uvtdorms.repository.dto.response.LaundryAppointmentsDto;
import com.uvtdorms.repository.dto.response.LightUserDto;
import com.uvtdorms.repository.dto.response.ListedRegisterRequestDto;
import com.uvtdorms.repository.dto.response.RegisterRequestDto;
import com.uvtdorms.repository.dto.response.StudentDetailsDto;
import com.uvtdorms.repository.dto.response.StudentLaundryAppointmentsDto;
import com.uvtdorms.repository.dto.response.StudentTicketsDto;
import com.uvtdorms.repository.dto.response.TicketDto;
import com.uvtdorms.repository.dto.response.WashingMachineDto;
import com.uvtdorms.repository.entity.Dorm;
import com.uvtdorms.repository.entity.DormAdministratorDetails;
import com.uvtdorms.repository.entity.Dryer;
import com.uvtdorms.repository.entity.Eveniment;
import com.uvtdorms.repository.entity.LaundryAppointment;
import com.uvtdorms.repository.entity.RegisterRequest;
import com.uvtdorms.repository.entity.Room;
import com.uvtdorms.repository.entity.StudentDetails;
import com.uvtdorms.repository.entity.Ticket;
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
                map(source.getAssociatedDryer().getDryerId(), destination.getAssociatedDryerId());
                map(source.getStatus(), destination.getStatusMachine());

                Converter<StatusMachine, Boolean> statusConverter = new Converter<StatusMachine, Boolean>() {
                    public Boolean convert(MappingContext<StatusMachine, Boolean> context) {
                        return context.getSource() != null && context.getSource().equals(StatusMachine.FUNCTIONAL);
                    }
                };
                using(statusConverter).map(source.getStatus(), destination.getIsAvailable());
            }
        });

        modelMapper.addMappings(new PropertyMap<WashingMachine, AvailableWashingMachineDto>() {
            @Override
            protected void configure() {
                using(ctx -> ((UUID) ctx.getSource()).toString()).map(source.getMachineId(), destination.getId());
                map(source.getMachineNumber(), destination.getName());
            }
        });

        modelMapper.addMappings(new PropertyMap<Dryer, AvailableDryerDto>() {
            @Override
            protected void configure() {
                using(ctx -> ((UUID) ctx.getSource()).toString()).map(source.getDryerId(), destination.getId());
                map(source.getDryerNumber(), destination.getName());
            }
        });

        modelMapper.addMappings(new PropertyMap<Dryer, DryerDto>() {
            @Override
            protected void configure() {
                using(ctx -> ((UUID) ctx.getSource()).toString()).map(source.getDryerId(), destination.getId());
                map(source.getDryerNumber(), destination.getName());
                map(source.getAssociatedWashingMachine().getMachineId(), destination.getAssociatedWashingMachineId());
                map(source.getStatus(), destination.getStatusMachine());

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

        modelMapper.addMappings(new PropertyMap<StudentDetails, DisplayStudentDetailsDto>() {
            @Override
            protected void configure() {
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

        modelMapper.addMappings(new PropertyMap<DormAdministratorDetails, DisplayDormAdministratorDetailsDto>() {
            @Override
            protected void configure() {
                map(source.getAdministrator().getEmail(), destination.getEmail());
                map(source.getAdministrator().getPhoneNumber(), destination.getPhoneNumber());
                map(source.getDorm().getDormName(), destination.getDormName());

            }
        });

        modelMapper.addMappings(new PropertyMap<LaundryAppointment, LaundryAppointmentsDto>() {
            @Override
            protected void configure() {
                map(source.getStudent().getUser().getEmail(), destination.getStudentEmail());
                map(source.getWashMachine().getMachineId(), destination.getWashingMachineId());
                map(source.getDryer().getDryerId(), destination.getDryerId());
                map(source.getIntervalBeginDate(), destination.getIntervalBeginDate());
            }
        });

        modelMapper.addMappings(new PropertyMap<LaundryAppointment, StudentLaundryAppointmentsDto>() {
            @Override
            protected void configure() {
                map(source.getWashMachine().getMachineNumber(), destination.getWashingMachineNumber());
                map(source.getDryer().getDryerNumber(), destination.getDryerNumber());
                map(source.getIntervalBeginDate(), destination.getIntervalBeginDate());
            }
        });

        modelMapper.addMappings(new PropertyMap<Ticket, TicketDto>() {
            @Override
            protected void configure() {
                map(source.getCreationDate(), destination.getCreationDate());
                map(source.getStatusTicket(), destination.getStatusTicket());
                map(source.getTipInterventie(), destination.getTipInterventie());
                map(source.getTitle(), destination.getTitle());
                map(source.getDescription(), destination.getDescription());
                map(source.isAlreadyAnuncement(), destination.isAlreadyAnuncement());
                map(source.getStudent().getUser().getEmail(), destination.getStudentEmail());
                map(source.getStudent().getRoom().getRoomNumber(), destination.getRoomNumber());
            }
        });

        modelMapper.addMappings(new PropertyMap<Ticket, StudentTicketsDto>() {
            @Override
            protected void configure() {
                map(source.getCreationDate(), destination.getCreationDate());
                map(source.getStatusTicket(), destination.getStatusTicket());
                map(source.getTipInterventie(), destination.getTipInterventie());
                map(source.getTitle(), destination.getTitle());
                map(source.getDescription(), destination.getDescription());
                map(source.isAlreadyAnuncement(), destination.isAlreadyAnuncement());
            }
        });

        modelMapper.addMappings(new PropertyMap<Dorm, DormDto>() {
            @Override
            protected void configure() {
                map(source.getDormName(), destination.getName());
                map(source.getAddress(), destination.getAddress());
                map(source.getDormAdministratorDetails().getAdministrator().getEmail(),
                        destination.getAdministratorEmail());
                map(source.getDormAdministratorDetails().getAdministrator().getFullName(),
                        destination.getAdministratorName());
                map(source.getIdString(), destination.getId());
            }
        });

        modelMapper.addMappings(new PropertyMap<DormAdministratorDetails, DormAdministratorDto>() {
            @Override
            protected void configure() {
                map(source.getAdministrator().getFullName(), destination.getName());
                map(source.getAdministrator().getEmail(), destination.getEmail());
            }
        });

        modelMapper.addMappings(new PropertyMap<DormAdministratorDetails, DetailedDormAdministratorDto>() {
            @Override
            protected void configure() {
                map(source.getAdministrator().getFullName(), destination.getName());
                map(source.getAdministrator().getEmail(), destination.getEmail());
                map(source.getDorm().getDormName(), destination.getDormName());
                map(source.getDorm().getIdString(), destination.getDormId());
                map(source.getAdministrator().getPhoneNumber(), destination.getPhoneNumber());
            }
        });

        modelMapper.addMappings(new PropertyMap<Dorm, AvailableDormDto>() {
            @Override
            protected void configure() {
                map(source.getIdString(), destination.getId());
                map(source.getDormName(), destination.getDormName());
            }
        });

        modelMapper.addMappings(new PropertyMap<Room, DetailedRoomDto>() {
            @Override
            protected void configure() {
                map(source.getRoomNumber(), destination.getRoomNumber());
                map(source.getNumberOfStudents(), destination.getNumberOfStudents());
            }
        });

        modelMapper.addMappings(new PropertyMap<StudentDetails, LightUserDto>() {
            @Override
            protected void configure() {
                map(source.getUser().getFullName(), destination.getName());
                map(source.getUser().getEmail(), destination.getEmail());
            }
        });

        modelMapper.addMappings(new PropertyMap<Eveniment, EvenimentDetailsDto>() {
            @Override
            protected void configure() {
                map(source.getIdString(), destination.getId());
                map(source.getTitle(), destination.getTitle());
                map(source.getDescription(), destination.getDescription());
                map(source.getCreatedBy().getAdministrator().getEmail(), destination.getDormAdministratorEmail());
                map(source.getCreatedBy().getAdministrator().getFullName(), destination.getDormAdministratorName());
                map(source.getCanPeopleAttend(), destination.getCanPeopleAttend());
                map(source.getNumberOfAttendees(), destination.getNumberOfAttendees());
                map(source.getStartDate(), destination.getStartDate());
            }
        });

        return modelMapper;
    }
}
