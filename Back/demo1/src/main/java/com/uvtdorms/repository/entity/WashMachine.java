package com.uvtdorms.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "wash_machines")
public class WashMachine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer machineId;
    private String machineNumber;

    @ManyToOne
    @JoinColumn(name = "dorm_id")
    private Dorm dorm;

    @OneToMany(mappedBy = "washMachine", cascade = CascadeType.ALL)
    private List<LaundryAppointment> laundryAppointments;

    @OneToOne(mappedBy = "washingMachine", cascade = CascadeType.ALL)
    private WashingMachineStatus washingMachineStatus;
}