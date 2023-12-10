package com.uvtdorms.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer roomId;
    private String roomNumber;

    @ManyToOne
    @JoinColumn(name = "dorm_id")
    private Dorm dorm;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<StudentDetails> studentDetails;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<RepairTicket> repairTickets;
}
