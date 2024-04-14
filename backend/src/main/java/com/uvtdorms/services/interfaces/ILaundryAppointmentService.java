package com.uvtdorms.services.interfaces;

import com.uvtdorms.exception.AppException;
import com.uvtdorms.repository.dto.request.CreateLaundryAppointmentDto;
import com.uvtdorms.repository.dto.request.GetFreeIntervalDto;
import com.uvtdorms.repository.dto.response.FreeIntervalsDto;

public interface ILaundryAppointmentService {
    public void createLaundryAppointment(CreateLaundryAppointmentDto createLaundryAppointmentDto) throws AppException;

    public FreeIntervalsDto getFreeIntervalsForCreatingAppointment(GetFreeIntervalDto freeIntervalDto);
}
