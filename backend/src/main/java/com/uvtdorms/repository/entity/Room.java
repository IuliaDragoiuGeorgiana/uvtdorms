package com.uvtdorms.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID roomId;
    private String roomNumber;

    @ManyToOne
    @JoinColumn(name = "dorm_id")
    private Dorm dorm;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<StudentDetails> studentDetails;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<RegisterRequest> roomRegisterRequests;
}
