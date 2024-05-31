package com.uvtdorms.repository.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

import com.uvtdorms.repository.entity.enums.StatusTicket;
import com.uvtdorms.repository.entity.enums.TipInterventie;
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tickets")
public class Ticket{

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID appointmentId;

    private LocalDateTime creationDate;

    @Enumerated(EnumType.STRING)
    private StatusTicket statusTicket;

    @Enumerated(EnumType.STRING)
    private TipInterventie tipInterventie;

    private String title;
    private String description;
    private boolean alreadyAnuncement;

    @ManyToOne
    @JoinColumn(name = "dorm_id")
    private Dorm dorm;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private StudentDetails student;
}