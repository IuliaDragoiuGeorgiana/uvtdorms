package com.uvtdorms.services;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.uvtdorms.exception.AppException;
import com.uvtdorms.repository.IModifiableLaundryAppointmentsRepository;
import com.uvtdorms.repository.IStudentDetailsRepository;
import com.uvtdorms.repository.dto.request.ModifiableLaundryAppointmentsIdDto;
import com.uvtdorms.repository.entity.ModifiableLaundryAppointment;
import com.uvtdorms.repository.entity.StudentDetails;
import com.uvtdorms.repository.entity.enums.StatusLaundry;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ModifiableLaundryAppointmentsService {
    private final IModifiableLaundryAppointmentsRepository modifiableLaundryAppointmentsRepository;
    private final IStudentDetailsRepository studentDetailsRepository;

    public void validateModifiableLaundryAppointments(
            String modifiableLaundryAppointmentId,
            String studentEmail) {
        UUID modifiableAppointmentId = UUID.fromString(modifiableLaundryAppointmentId);
        ModifiableLaundryAppointment modifiableLaundryAppointment = modifiableLaundryAppointmentsRepository
                .findById(modifiableAppointmentId)
                .orElseThrow(() -> new AppException("Modifiable Laundry Appointment not found", HttpStatus.NOT_FOUND));

        StudentDetails student = studentDetailsRepository.findByUserEmail(studentEmail)
                .orElseThrow(() -> new AppException("Student not found", HttpStatus.NOT_FOUND));

        if (!modifiableLaundryAppointment.getLaundryAppointment().getStudent().equals(student)) {
            throw new AppException("Student is not the owner of the appointment", HttpStatus.BAD_REQUEST);
        }

        if (modifiableLaundryAppointment.isModified()) {
            throw new AppException("Laundry Appointment is already modified", HttpStatus.BAD_REQUEST);
        }

        if (!modifiableLaundryAppointment.getLaundryAppointment().getIntervalBeginDate().isAfter(LocalDateTime.now())) {
            throw new AppException("Laundry Appointment is not available for modification", HttpStatus.BAD_REQUEST);
        }
    }

    public void keepLaundryAppointmentWithoutDryer(
            ModifiableLaundryAppointmentsIdDto modifiableLaundryAppointmentIdDto) {
        UUID modifiableAppointmentId = UUID.fromString(modifiableLaundryAppointmentIdDto.id());
        ModifiableLaundryAppointment modifiableLaundryAppointment = modifiableLaundryAppointmentsRepository
                .findById(modifiableAppointmentId)
                .orElseThrow(() -> new AppException("Modifiable Laundry Appointment not found", HttpStatus.NOT_FOUND));

        modifiableLaundryAppointment.setModified(true);
        modifiableLaundryAppointment.getLaundryAppointment().setStatusLaundry(StatusLaundry.SCHEDULED);
        modifiableLaundryAppointment.getLaundryAppointment().setDryer(null);
        modifiableLaundryAppointmentsRepository.save(modifiableLaundryAppointment);
    }

    public void cancelLaundryAppointment(
            ModifiableLaundryAppointmentsIdDto modifiableLaundryAppointmentIdDto) {
        UUID modifiableAppointmentId = UUID.fromString(modifiableLaundryAppointmentIdDto.id());
        ModifiableLaundryAppointment modifiableLaundryAppointment = modifiableLaundryAppointmentsRepository
                .findById(modifiableAppointmentId)
                .orElseThrow(() -> new AppException("Modifiable Laundry Appointment not found", HttpStatus.NOT_FOUND));

        modifiableLaundryAppointment.setModified(true);
        modifiableLaundryAppointment.getLaundryAppointment().setStatusLaundry(StatusLaundry.CANCELED);
        modifiableLaundryAppointmentsRepository.save(modifiableLaundryAppointment);
    }
}
