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
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userId;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String phoneNumber;
    private String password;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private StudentDetails studentDetails;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<LaundryAppointment> laundryAppointments;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<RepairTicket> repairTickets;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Announcement> announcements;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserRoles userRoles;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private ActiveUsers activeUsers;
}
