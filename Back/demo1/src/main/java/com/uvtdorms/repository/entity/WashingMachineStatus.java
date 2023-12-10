package com.uvtdorms.repository.entity;

import com.uvtdorms.repository.entity.enums.StatusMachine;
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
@Table(name = "washing_machine_status")
public class WashingMachineStatus {
    @Id
    @OneToOne
    @MapsId
    @JoinColumn(name = "machine_id")
    private WashMachine washingMachine;

    @Enumerated(EnumType.STRING)
    private StatusMachine statusWashingMachine;
}
