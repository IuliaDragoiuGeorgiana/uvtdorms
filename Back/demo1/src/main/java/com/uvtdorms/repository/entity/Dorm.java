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
@Table(name = "dorms")
public class Dorm {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer dormId;
    private String dormName;
    private String address;

    @OneToMany(mappedBy = "dorm", cascade = CascadeType.ALL)
    private List<StudentDetails> studentDetails;

    @OneToMany(mappedBy = "dorm", cascade = CascadeType.ALL)
    private List<Room> rooms;

    @OneToMany(mappedBy = "dorm", cascade = CascadeType.ALL)
    private List<WashMachine> washMachines;

    @OneToMany(mappedBy = "dorm", cascade = CascadeType.ALL)
    private List<Dryer> dryers;



}