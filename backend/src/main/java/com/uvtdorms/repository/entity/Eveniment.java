package com.uvtdorms.repository.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "events")
public class Eveniment {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    private String title;

    @Column(columnDefinition = "text")
    private String description;

    @ManyToOne
    private DormAdministratorDetails createdBy;

    private Boolean canPeopleAttend;

    @ManyToMany
    private List<User> attendees;

    @ManyToOne
    private Dorm dorm;

    private LocalDateTime startDate;
    @Builder.Default
    private LocalDateTime createdOnDate = LocalDateTime.now();

    @Transactional
    public int getNumberOfAttendees() {
        if (attendees == null)
            return 0;
        return attendees.size();
    }

    public String getIdString() {
        return id.toString();
    }
}
