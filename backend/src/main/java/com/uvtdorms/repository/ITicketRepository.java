package com.uvtdorms.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Repository;

import com.uvtdorms.repository.entity.Dorm;
import com.uvtdorms.repository.entity.Ticket;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ITicketRepository extends JpaRepository<Ticket, UUID> {
    @Transactional
    List<Ticket> findByDorm(Dorm dorm);
}
