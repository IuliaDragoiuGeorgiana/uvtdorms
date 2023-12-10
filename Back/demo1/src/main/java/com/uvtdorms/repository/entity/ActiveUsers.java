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
@Table(name = "active_users")
public class ActiveUsers {
    @Id
    @OneToOne
    @JoinColumn(name = "user_id")
    @MapsId
    private User user;
}
