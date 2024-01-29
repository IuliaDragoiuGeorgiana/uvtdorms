package com.uvtdorms.repository.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "dorm_administrator_details")
public class DormAdministratorDetails {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID dormAdministratorDetailsId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "dorm_id")
    private Dorm dorm;

    public DormAdministratorDetails(User user, Dorm dorm) {
        this.user = user;
        this.dorm = dorm;
    }
}
