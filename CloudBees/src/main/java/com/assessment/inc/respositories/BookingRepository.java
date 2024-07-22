package com.assessment.inc.respositories;

import com.assessment.inc.entites.Inventory;
import com.assessment.inc.entites.Ticket;
import com.assessment.inc.entites.TrainKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Ticket, TrainKey> {
    List<Ticket> findByEmail(String emailAddress);
    Optional<Ticket> findByTicketId(String ticketId);
    List<Ticket> findByTrainId(String trainId);

    List<Ticket> findBySectionAndTrainId(String section, String trainId);
}
