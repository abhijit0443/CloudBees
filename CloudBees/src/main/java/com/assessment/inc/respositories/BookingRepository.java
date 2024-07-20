package com.assessment.inc.respositories;

import com.assessment.inc.entites.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Ticket, String> {
    List<Ticket> findByEmail(String emailAddress);
    Optional<Ticket> findByTicketId(String ticketId);


}