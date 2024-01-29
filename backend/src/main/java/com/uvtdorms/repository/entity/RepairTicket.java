package com.uvtdorms.repository.entity;

import com.uvtdorms.repository.entity.enums.StatusTicket;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "repair_tickets")
public class RepairTicket {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID ticketId;
    @Lob
    private String description;
    private Date creationDate;
    private Date closureDate;

    @ManyToOne
    @JoinColumn(name = "reported_by_user_id")
    private User user;


    @Enumerated(EnumType.STRING)
    private StatusTicket statusTicket;
}
