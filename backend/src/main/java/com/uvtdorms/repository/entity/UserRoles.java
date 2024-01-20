package com.uvtdorms.repository.entity;

import com.uvtdorms.repository.entity.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_roles")
public class UserRoles {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID user_id;
    @OneToOne
    @JoinColumn(name = "user_id")
    @MapsId
    private User user;

    @Enumerated(EnumType.STRING)
    private Role role;
}
