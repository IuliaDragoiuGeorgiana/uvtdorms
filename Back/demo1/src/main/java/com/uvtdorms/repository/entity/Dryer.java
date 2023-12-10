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
@Table(name = "dryers")
public class Dryer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer dryerId;
    private String dryerNumber;

    @ManyToOne
    @JoinColumn(name = "dorm_id")
    private Dorm dorm;

    @OneToMany(mappedBy = "dryer", cascade = CascadeType.ALL)
    private List<LaundryAppointment> laundryAppointments;

    @OneToOne(mappedBy = "dryer", cascade = CascadeType.ALL)
    private DryerStatus dryerStatus;
}
