package com.uvtdorms.services.interfaces;

import java.util.List;

import com.uvtdorms.repository.dto.request.CreateLaundryAppointmentDto;
import com.uvtdorms.repository.dto.request.FreeIntervalDto;

public interface ILaundryAppointmentService {
    public void createLaundryAppointment(CreateLaundryAppointmentDto createLaundryAppointmentDto) throws Exception;
    public List<Integer> getFreeIntervalsForCreatingAppointment(FreeIntervalDto freeIntervalDto) throws Exception;
}
