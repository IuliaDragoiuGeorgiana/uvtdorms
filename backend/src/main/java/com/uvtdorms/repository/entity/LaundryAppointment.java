package com.uvtdorms.repository.entity;

import com.uvtdorms.repository.entity.enums.StatusLaundry;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "laundry_appointments")
public class LaundryAppointment {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID appointmentId;
    private LocalDateTime creationDate;
    private LocalDateTime intervalBeginDate;
    private LocalDateTime intervalEndDate;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private StudentDetails student;

    @ManyToOne
    @JoinColumn(name = "wash_machine_id")
    private WashingMachine washMachine;

    @ManyToOne
    @JoinColumn(name = "dryer_id")
    private Dryer dryer;

   @Enumerated(EnumType.STRING)
    private StatusLaundry statusLaundry;

    public LaundryAppointment(
            LocalDateTime intervalBeginDate,
            StudentDetails student,
            WashingMachine washMachine,
            Dryer dryer)
    {
        this.intervalBeginDate = intervalBeginDate;
        this.student = student;
        this.washMachine = washMachine;
        this.dryer = dryer;
        this.statusLaundry = StatusLaundry.SCHEDULED;
        this.intervalEndDate= intervalBeginDate.plusHours(2);
        this.creationDate=LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "LaundryAppointment [appointmentId=" + appointmentId + ", creationDate=" + creationDate
                + ", intervalBeginDate=" + intervalBeginDate + ", intervalEndDate=" + intervalEndDate + ", student="
                + student + ", washMachine=" + washMachine + ", dryer=" + dryer + ", statusLaundry=" + statusLaundry
                + "]";
    }
}
