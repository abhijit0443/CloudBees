package com.assessment.inc.services;

import com.assessment.inc.entites.Ticket;
import com.assessment.inc.entites.TrainDetails;
import com.assessment.inc.exceptions.ExceededCapacityException;
import com.assessment.inc.exceptions.TicketCancellationException;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {

    Ticket bookTicket(String fromLocation, String toLocation, String trainId, LocalDate date,
                                 String firstName, String lastName, String email) throws ExceededCapacityException;

    Ticket getTicket(String ticketId) ;

   // List<Ticket> getUsersBySection(String section) ;
    List<TrainDetails> findByTrainId(String trainId);

     List<Ticket> getTicketsByEmail(String emailAddress);

    void cancelTicket(String ticketId) throws TicketCancellationException;

    Ticket modifySeat(String ticketId, int newSeatNumber);

    List<Ticket> getUserDetailsBySecAndByTrainId(String section, String trainId);
}
