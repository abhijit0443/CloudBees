
package com.assessment.inc.controllers;
import com.assessment.inc.controllers.BookingController;
import com.assessment.inc.dto.BookingRequest;
import com.assessment.inc.entites.Ticket;
import com.assessment.inc.entites.TrainDetails;
import com.assessment.inc.exceptions.ExceededCapacityException;
import com.assessment.inc.exceptions.TicketCancellationException;
import com.assessment.inc.services.BookingService;
import com.assessment.inc.util.Constants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BookingControllerTest {
    @Mock
    private BookingService bookingService;
    @InjectMocks
    private BookingController bookingController;
    @Test
    public void testFindByTrainId() {
        String trainId = "12345";
        List<TrainDetails> trainDetailsList = Arrays.asList(new TrainDetails(), new TrainDetails());
        when(bookingService.findByTrainId(trainId)).thenReturn(trainDetailsList);
        List<TrainDetails> result = bookingController.findByTrainId(trainId);
        assertEquals(2, result.size());
        verify(bookingService, times(1)).findByTrainId(trainId);
    }

    @Test
    public void testBookTicket() throws ExceededCapacityException {
        BookingRequest request = new BookingRequest();
        request.setTrainId("12345");
        request.setFromLocation("Bangalore");
        request.setToLocation("Dhanbad");
        request.setFirstName("Abhijit");
        request.setLastName("Mandal");
        request.setDate(LocalDate.now());
        request.setEmailAddress("abhijit@gmail.com");
        Ticket bookedTicket = new Ticket();
        when(bookingService.bookTicket(anyString(), anyString(), anyString(), any(LocalDate.class), anyString(), anyString(), anyString())).thenReturn(bookedTicket);
        ResponseEntity<Ticket> responseEntity = bookingController.bookTicket(request);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(bookedTicket, responseEntity.getBody());
        verify(bookingService, times(1)).bookTicket(eq("Bangalore"), eq("Dhanbad"), eq("12345"), eq(LocalDate.now()), eq("Abhijit"), eq("Mandal"), eq("abhijit@gmail.com"));
    }

    @Test
    public void testCancelTicket() throws TicketCancellationException {
        String ticketId = "ea789c2c-780e-46d8-adf8-37dd3be390ab";
        doNothing().when(bookingService).cancelTicket(ticketId);
        ResponseEntity<String> responseEntity = bookingController.cancelTicket(ticketId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(String.format(Constants.TICKET_CANCELLED_SUCCESS, ticketId), responseEntity.getBody());
        verify(bookingService, times(1)).cancelTicket(ticketId);
    }

    @Test
    public void testGetReceiptDetails() {
        String ticketId = "ea789c2c-780e-46d8-adf8-37dd3be390ab";
        Ticket ticket = new Ticket();
        when(bookingService.getTicket(ticketId)).thenReturn(ticket);
        Ticket result = bookingController.getReceiptDetails(ticketId);
        assertEquals(ticket, result);
        verify(bookingService, times(1)).getTicket(ticketId);
    }

    @Test
    public void testModifySeat() {
        String ticketId = "ea789c2c-780e-46d8-adf8-37dd3be390ab";
        int newSeatNumber = 10;
        Ticket modifiedTicket = new Ticket();
        when(bookingService.modifySeat(ticketId, newSeatNumber)).thenReturn(modifiedTicket);
        ResponseEntity<Ticket> responseEntity = bookingController.modifySeat(ticketId, newSeatNumber);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(modifiedTicket, responseEntity.getBody());
        verify(bookingService, times(1)).modifySeat(ticketId, newSeatNumber);
    }
}
