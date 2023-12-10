package com.uvtdorms.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "laundry_appointments")
public class LaundryAppointment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer appointmentId;
    private Date creationDate;
    private Date closureDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "wash_machine_id")
    private WashMachine washMachine;

    @ManyToOne
    @JoinColumn(name = "dryer_id")
    private Dryer dryer;

    @OneToOne(mappedBy = "laundryAppointment", cascade = CascadeType.ALL)
    private LaundryAppointmentStatus laundryAppointmentStatus;
}
