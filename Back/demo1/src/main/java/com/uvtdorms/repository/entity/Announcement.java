package com.uvtdorms.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "announcements")
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer announcementId;
    private String title;
    @Lob
    private String content;
    private Date creationDate;

    @ManyToOne
    @JoinColumn(name = "created_by_user_id")
    private User user;
}
