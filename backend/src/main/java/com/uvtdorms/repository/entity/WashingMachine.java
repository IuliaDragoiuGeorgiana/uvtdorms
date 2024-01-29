package com.uvtdorms.repository.entity;

import com.uvtdorms.repository.entity.enums.StatusMachine;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "wash_machines")
public class WashingMachine {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID machineId;
    private String machineNumber;

    @ManyToOne
    @JoinColumn(name = "dorm_id")
    private Dorm dorm;

    @OneToMany(mappedBy = "washMachine", cascade = CascadeType.ALL)
    private List<LaundryAppointment> laundryAppointments;

    @Enumerated(EnumType.STRING)
    private StatusMachine status;

    public WashingMachine(String machineNumber, Dorm dorm, StatusMachine status) {
        this.machineNumber = machineNumber;
        this.dorm = dorm;
        this.status=status;
    }

}