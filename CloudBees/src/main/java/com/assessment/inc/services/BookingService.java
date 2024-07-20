package com.assessment.inc.services;

import com.assessment.inc.entites.Ticket;
import com.assessment.inc.entites.TrainDetails;
import com.assessment.inc.exceptions.ExceededCapacityException;
import com.assessment.inc.exceptions.TicketCancellationException;

import java.util.List;

public interface BookingService {

    Ticket bookTicket(String fromLocation, String toLocation,String trainId,
                                 String firstName, String lastName,String email) throws ExceededCapacityException;

    Ticket getTicket(String ticketId) ;

   // List<Ticket> getUsersBySection(String section) ;
     TrainDetails getTrainById(String trainId);

     List<Ticket> getTicketsByEmail(String emailAddress);

    void cancelTicket(String ticketId) throws TicketCancellationException;

    Ticket modifySeat(String ticketId, int newSeatNumber);
}
