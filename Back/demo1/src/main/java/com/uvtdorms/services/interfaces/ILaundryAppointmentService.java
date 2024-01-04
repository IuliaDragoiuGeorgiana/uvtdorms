package com.uvtdorms.services.interfaces;

import com.uvtdorms.repository.dto.request.CreateLaundryAppointmentDto;

public interface ILaundryAppointmentService {

    public void createLaundryAppointment(CreateLaundryAppointmentDto createLaundryAppointmentDto) throws Exception;
}
