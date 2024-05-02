package com.uvtdorms.repository.entity;

import com.uvtdorms.repository.entity.enums.StatusMachine;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @OneToOne
    @JoinColumn(name = "associated_washing_machine_id")
    private WashingMachine associatedWashingMachine;
}
