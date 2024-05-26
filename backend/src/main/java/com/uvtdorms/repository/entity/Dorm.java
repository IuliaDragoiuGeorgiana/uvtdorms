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
@Table(name = "dorms")
public class Dorm {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID dormId;

    private String dormName;

    private String address;

    @OneToMany(mappedBy = "dorm", cascade = CascadeType.ALL)
    private List<Room> rooms;

    @OneToMany(mappedBy = "dorm", cascade = CascadeType.ALL)
    private List<WashingMachine> washMachines;

    @OneToMany(mappedBy = "dorm", cascade = CascadeType.ALL)
    private List<Dryer> dryers;

    @OneToOne(mappedBy = "dorm", cascade = CascadeType.ALL)
    private DormAdministratorDetails dormAdministratorDetails;

    @OneToMany(mappedBy = "dorm", cascade = CascadeType.ALL)
    private List<Ticket> tickets;
}