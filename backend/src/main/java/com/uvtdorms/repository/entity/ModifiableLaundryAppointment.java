package com.uvtdorms.repository.entity;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "laundry_appointments_to_be_modified")
public class ModifiableLaundryAppointment {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID modifiableAppointmentId;

    @OneToOne
    private LaundryAppointment laundryAppointment;

    private boolean isModified;

    public ModifiableLaundryAppointment(LaundryAppointment laundryAppointment) {
        this.laundryAppointment = laundryAppointment;
        this.isModified = false;
    }
}
