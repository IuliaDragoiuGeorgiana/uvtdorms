package com.uvtdorms.repository;
import java.util.UUID;
import org.springframework.stereotype.Repository;
import com.uvtdorms.repository.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface ITicketRepository extends JpaRepository<Ticket, UUID> {
   
}
