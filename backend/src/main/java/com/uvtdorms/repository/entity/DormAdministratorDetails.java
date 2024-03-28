package com.uvtdorms.repository.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "dorm_administrator_details")
public class DormAdministratorDetails {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID dormAdministratorDetailsId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User administrator;

    @OneToOne
    @JoinColumn(name = "dorm_id")
    private Dorm dorm;
}
