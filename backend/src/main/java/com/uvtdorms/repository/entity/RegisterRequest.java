package com.uvtdorms.repository.entity;

import java.time.LocalDate;
import java.util.UUID;

import com.uvtdorms.repository.entity.enums.RegisterRequestStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "register_requests")
public class RegisterRequest {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID registerRequestId;

    @ManyToOne
    @JoinColumn(name = "studentId")
    private StudentDetails student;

    @ManyToOne
    @JoinColumn(name = "roomId")
    private Room room;

    private LocalDate createdOn;
    private RegisterRequestStatus status;
}

