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
@Table(name = "dryer_status")
public class DryerStatus {
    @Id
    @OneToOne
    @MapsId
    @JoinColumn(name = "dryer_id")
    private Dryer dryer;

    @Enumerated(EnumType.STRING)
    private StatusMachine statusDryer;
}
