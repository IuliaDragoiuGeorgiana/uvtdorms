package com.uvtdorms.repository;

import com.uvtdorms.repository.entity.Dryer;
import com.uvtdorms.repository.entity.LaundryAppointment;
import com.uvtdorms.repository.entity.StudentDetails;
import com.uvtdorms.repository.entity.WashingMachine;
import com.uvtdorms.repository.entity.enums.StatusLaundry;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILaundryAppointmentRepository extends JpaRepository<LaundryAppointment, UUID> {
    @Transactional
    public List<LaundryAppointment> findByWashMachine(WashingMachine washingMachine);

    @Transactional
    public List<LaundryAppointment> findByDryer(Dryer dryer);

    @Transactional
    public Optional<LaundryAppointment> findByStudentAndStatusLaundry(StudentDetails student, StatusLaundry status);
}
