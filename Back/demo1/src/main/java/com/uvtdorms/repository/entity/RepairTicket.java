package com.uvtdorms.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "repair_tickets")
public class RepairTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer ticketId;
    @Lob
    private String description;
    private Date creationDate;
    private Date closureDate;

    @ManyToOne
    @JoinColumn(name = "reported_by_user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @OneToOne(mappedBy = "repairTicket", cascade = CascadeType.ALL)
    private RepairTicketStatus repairTicketStatus;
}
