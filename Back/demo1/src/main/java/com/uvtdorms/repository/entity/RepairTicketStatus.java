package com.uvtdorms.repository.entity;

import com.uvtdorms.repository.entity.enums.StatusTicket;
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
@Table(name = "washing_machine_status")
public class RepairTicketStatus {
    @Id
    @OneToOne
    @MapsId
    @JoinColumn(name = "ticket_id")
    private RepairTicket repairTicket;

    @Enumerated(EnumType.STRING)
    private StatusTicket statusTicket;
}
