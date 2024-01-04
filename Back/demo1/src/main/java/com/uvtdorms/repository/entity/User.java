package com.uvtdorms.repository.entity;


import com.uvtdorms.repository.entity.enums.Role;
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
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID userId;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String phoneNumber;
    private String password;

    //Just for testing--InitialDataLoader
    public User(String firstName, String lastName, String email, String phoneNumber, String password, Role role, Boolean isActive) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.role = role;
        this.isActive = isActive;
    }

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private StudentDetails studentDetails;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<LaundryAppointment> laundryAppointments;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<RepairTicket> repairTickets;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Announcement> announcements;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private  DormAdministratorDetails dormAdministratorDetails;

    private  Boolean isActive;


}
