package com.assessment.inc.controllers;

import com.assessment.inc.dto.BookingRequest;
import com.assessment.inc.entites.Ticket;
import com.assessment.inc.entites.TrainDetails;
import com.assessment.inc.exceptions.ExceededCapacityException;
import com.assessment.inc.exceptions.TicketCancellationException;
import com.assessment.inc.services.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/trains/")
public class BookingController {

    private final  BookingService bookingService;
    public BookingController(BookingService bookingService){
        this.bookingService=bookingService;
    }



    @GetMapping("/trainById/{trainId}")
    public TrainDetails getTrainById(@PathVariable String trainId) {
        return bookingService.getTrainById(trainId);

    }


    /*Create API where you can submit a purchase for a ticket
    * From, To, User , price paid.
    User should include first and last name, email address*/
   // @PreAuthorize("hasAuthority('SCOPE_internal') || hasAuthority('Admin')")
    @PostMapping("/book")
    public ResponseEntity<Ticket> bookTicket(@RequestBody BookingRequest request) throws ExceededCapacityException {
        String trainId = request.getTrainId();
        String fromLocation = request.getFromLocation();
        String toLocation = request.getToLocation();
        String firstName = request.getFirstName();
        String lastName = request.getLastName();
        String emailAddress = request.getEmailAddress();
    return ResponseEntity.status(HttpStatus.CREATED).body(bookingService.bookTicket(fromLocation, toLocation, trainId,firstName, lastName, emailAddress));

    }



    @DeleteMapping("cancel/{ticketId}")
    public ResponseEntity<String> cancelTicket(@PathVariable String ticketId) {
        try {
            bookingService.cancelTicket(ticketId);
            return ResponseEntity.ok().body("Ticket with ID " + ticketId + " has been canceled");
        } catch (TicketCancellationException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

   /* An API that lets you view the users and seat they are allocated by the requested section*/
  /*  @GetMapping("/users-by-section/{section}")
    public List<Ticket> getUsersBySection(@PathVariable String section) {
        return bookingService.getUsersBySection(section);
    }*/

    /*An API that shows the details of the receipt for the user*/
    @GetMapping("/ticket/{ticketId}")
    public Ticket getReceiptDetails(@PathVariable String ticketId) {
        return bookingService.getTicket(ticketId);
    }



    @PutMapping("/modify-seat/{ticketId}")
    public ResponseEntity<Ticket> modifySeat(
            @PathVariable String ticketId,
            @RequestParam int newSeatNumber) {

        Ticket modifiedTicket = bookingService.modifySeat(ticketId, newSeatNumber);
        return ResponseEntity.ok().body(modifiedTicket);
    }


}