package com.uvtdorms.repository;

import com.uvtdorms.repository.entity.Announcement;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAnnouncementRepository extends JpaRepository<Announcement, UUID> {
}
