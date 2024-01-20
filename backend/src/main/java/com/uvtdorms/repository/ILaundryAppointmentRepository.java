package com.uvtdorms.repository;

import com.uvtdorms.repository.entity.LaundryAppointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILaundryAppointmentRepository extends JpaRepository<LaundryAppointment,Long> {
}
