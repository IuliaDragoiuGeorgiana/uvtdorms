package com.uvtdorms.services.interfaces;

import com.uvtdorms.repository.dto.request.CreateLaundryAppointmentDto;

import java.util.List;

public interface ILaundryAppointmentService {

    public void createLaundryAppointment(CreateLaundryAppointmentDto createLaundryAppointmentDto) throws Exception;
    public List<Integer> freeInterval ()
}
