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
@Table(name = "dryers")
public class Dryer {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID dryerId;
    private String dryerNumber;

    @ManyToOne
    @JoinColumn(name = "dorm_id")
    private Dorm dorm;

    @OneToMany(mappedBy = "dryer", cascade = CascadeType.ALL)
    private List<LaundryAppointment> laundryAppointments;

    @Enumerated(EnumType.STRING)
    private StatusMachine status;

    public Dryer(String dryerNumber, Dorm dorm, StatusMachine status) {
        this.dryerNumber = dryerNumber;
        this.dorm = dorm;
        this.status = status;
    }
}
