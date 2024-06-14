package com.uvtdorms.repository.entity;

import com.uvtdorms.repository.entity.enums.Role;
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

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private StudentDetails studentDetails;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<RepairTicket> repairTickets;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(mappedBy = "administrator", cascade = CascadeType.ALL)
    private DormAdministratorDetails dormAdministratorDetails;

    @ManyToMany
    private List<Eveniment> attendingToEvents;

    private Boolean isActive;

    @Lob
    private byte[] profilePicture;

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
