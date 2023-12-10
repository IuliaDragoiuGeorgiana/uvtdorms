package com.uvtdorms.repository.entity;

import com.uvtdorms.repository.entity.enums.StatusLaundry;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "laundry_appointment_status")
public class LaundryAppointmentStatus {
    @Id
    @OneToOne
    @MapsId
    @JoinColumn(name = "appointment_id")
    private LaundryAppointment laundryAppointment;

    @Enumerated(EnumType.STRING)
    private StatusLaundry statusLaundry;
}
