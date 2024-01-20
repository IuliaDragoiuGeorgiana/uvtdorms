package com.uvtdorms.repository;

import com.uvtdorms.repository.entity.RepairTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRepairTicketRepository extends JpaRepository<RepairTicket,Long> {
}
