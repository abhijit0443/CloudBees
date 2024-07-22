package com.assessment.inc.controllers;

import com.assessment.inc.dto.BookingRequest;
import com.assessment.inc.entites.Ticket;
import com.assessment.inc.entites.TrainDetails;
import com.assessment.inc.exceptions.ExceededCapacityException;
import com.assessment.inc.exceptions.TicketCancellationException;
import com.assessment.inc.services.BookingService;
import com.assessment.inc.services.UserService;
import com.assessment.inc.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(Constants.API_BASE_PATH)
public class BookingController {
    private static final Logger logger = LoggerFactory.getLogger(BookingController.class);

    private final  BookingService bookingService;
    public BookingController(BookingService bookingService){
        this.bookingService=bookingService;

    }
    @GetMapping(Constants.API_TRAIN_BY_ID)
    public List<TrainDetails> findByTrainId(@PathVariable @NotBlank(message = Constants.TRAIN_ID_REQUIRED) String trainId) {
        logger.info("Fetching train details for trainId: {}", trainId);
        return bookingService.findByTrainId(trainId);

    }
    @Validated
    @PostMapping(Constants.API_BOOK_TICKET)
    public ResponseEntity<Ticket> bookTicket(@Valid @RequestBody BookingRequest request) throws ExceededCapacityException {
        String trainId = request.getTrainId();
        String fromLocation = request.getFromLocation();
        String toLocation = request.getToLocation();
        String firstName = request.getFirstName();
        String lastName = request.getLastName();
        LocalDate date = request.getDate();
        String emailAddress = request.getEmailAddress();
        logger.info("Booking ticket for trainId: {}, from: {}, to: {}, date: {}, email: {}", trainId, fromLocation, toLocation, date, emailAddress);

        Ticket bookedTicket = bookingService.bookTicket(fromLocation, toLocation, trainId, date, firstName, lastName, emailAddress);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookedTicket);
    }
    @DeleteMapping(Constants.API_CANCEL_TICKET)
    public ResponseEntity<String> cancelTicket(@PathVariable @NotBlank(message = Constants.TICKET_ID_NOT_BLANK) String ticketId) {
        try {
            bookingService.cancelTicket(ticketId);
            logger.info("Cancelled ticket with ID: {}", ticketId);
            return ResponseEntity.ok().body(String.format(Constants.TICKET_CANCELLED_SUCCESS, ticketId));
        } catch (TicketCancellationException ex) {
            logger.error("Error cancelling ticket with ID: {}", ticketId, ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
    /*An API that shows the details of the receipt for the user*/
    @GetMapping(Constants.API_TRAIN_BY_TICKET_ID)
    public Ticket getReceiptDetails(@PathVariable @NotBlank(message = Constants.TICKET_ID_NOT_BLANK) String ticketId) {
        logger.info("Fetching receipt details for ticketId: {}", ticketId);
        return bookingService.getTicket(ticketId);
    }

    /*An API that shows the details of the receipt for the user*/
    @GetMapping(Constants.API_USER_DETAILS_BY_SEC)
    public List<Ticket> getLisOfUserDetailsByTrainSection(@RequestParam @NotBlank(message = Constants.SECTION_NOT_REQUIRED) String section,@RequestParam @NotBlank(message = Constants.TRAIN_ID_REQUIRED)String trainId) {
        logger.info("Fetching receipt details for section {} and trainid {}", section,trainId);
        List<Ticket>  tickets = bookingService.getUserDetailsBySecAndByTrainId(section,trainId);
        return tickets;
    }

    /*An API that shows the details of the receipt for the user*/
    @GetMapping(Constants.API_TRAIN_BY_EMAIL_ID)
    public List<Ticket> getTicketsByEmail(@PathVariable @NotBlank(message = Constants.EMAIL_ID_NOT_BLANK) String email) {
        logger.info("Fetching receipt details for Email ID: {}", email);
        return bookingService.getTicketsByEmail(email);
      //  return ResponseEntity.ok().body(modifiedTicket);
    }
    @PutMapping(Constants.API_MODIFY_SEAT)
    public ResponseEntity<Ticket> modifySeat(
            @PathVariable @NotBlank(message = Constants.TICKET_ID_NOT_BLANK) String ticketId,
            @RequestParam int newSeatNumber) {
        logger.info("Modifying seat for ticketId: {} to seat number: {}", ticketId, newSeatNumber);

        Ticket modifiedTicket = bookingService.modifySeat(ticketId, newSeatNumber);
        return ResponseEntity.ok().body(modifiedTicket);
    }

}
