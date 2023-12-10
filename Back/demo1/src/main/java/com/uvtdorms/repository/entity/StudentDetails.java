package com.uvtdorms.repository.entity;

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
@Table(name = "students_details")
public class StudentDetails {
    private String cnp;
    private String matriculationNumber;

    @Id
    @OneToOne
    @JoinColumn(name = "user_id")
    @MapsId
    private User user;

    @ManyToOne
    @JoinColumn(name = "dorm_id")
    private Dorm dorm;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;
}
