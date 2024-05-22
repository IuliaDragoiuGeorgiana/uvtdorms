package com.uvtdorms.repository.entity;

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
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "students_details")
public class StudentDetails {
    private String matriculationNumber;

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID student_id;

    @OneToOne
    @JoinColumn(name = "user_id")
    @MapsId
    private User user;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<LaundryAppointment> laundryAppointments;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<RegisterRequest> studentRegisterRequests;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Ticket> tickets;
    
    public StudentDetails(String matriculationNumber, User user, Room room) {
        this.matriculationNumber = matriculationNumber;
        this.user = user;
        this.room = room;
    }

}
