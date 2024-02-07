package com.uvtdorms.services.interfaces;

import java.util.List;

import com.uvtdorms.exception.AppException;
import com.uvtdorms.repository.dto.request.CreateLaundryAppointmentDto;
import com.uvtdorms.repository.dto.request.FreeIntervalDto;

public interface ILaundryAppointmentService {
    public void createLaundryAppointment(CreateLaundryAppointmentDto createLaundryAppointmentDto) throws AppException;

    public List<Integer> getFreeIntervalsForCreatingAppointment(FreeIntervalDto freeIntervalDto);
}
