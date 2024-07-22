package com.assessment.inc.services.impl;

import com.assessment.inc.entites.Inventory;
import com.assessment.inc.entites.Ticket;
import com.assessment.inc.entites.TrainDetails;
import com.assessment.inc.exceptions.ExceededCapacityException;
import com.assessment.inc.exceptions.ResourceNotFoundException;
import com.assessment.inc.exceptions.TicketCancellationException;
import com.assessment.inc.respositories.BookingRepository;
import com.assessment.inc.services.InventoryService;
import com.assessment.inc.services.TrainDetailsService;
import com.assessment.inc.services.impl.BookingServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BookingServiceImplTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private InventoryService inventoryService;

    @Mock
    private TrainDetailsService trainDetailsService;

    @InjectMocks
    private BookingServiceImpl bookingService;

    @Before
    public void setup() {
        Inventory mockInventory = new Inventory();
        mockInventory.setTrainId("12345");
        mockInventory.setTrainName("HowrahYashwanthpur");
        mockInventory.setDate(LocalDate.of(2024, 8, 30));
        mockInventory.setSeats(100);
        mockInventory.setAvaliableSeats(100);
        mockInventory.setFare(20.0);

        when(inventoryService.findByTrainIdAndDate(eq("12345"), any(LocalDate.class))).thenReturn(mockInventory);

        TrainDetails mockTrainDetails = new TrainDetails();
        mockTrainDetails.setTrainId("12345");
        mockTrainDetails.setTrainName("HowrahYashwanthpur");
        mockTrainDetails.setDate(LocalDate.of(2024, 8, 30));
        mockTrainDetails.setFromLocation("Howrah");
        mockTrainDetails.setToLocation("Yashwanthpur");
        mockTrainDetails.setAvailableSeats(100);

        when(trainDetailsService.findByTrainIdAndDate(eq("12345"), any(LocalDate.class))).thenReturn(mockTrainDetails);
    }

    @Test
    public void testCancelTicketSuccess() throws TicketCancellationException {

        Ticket ticket = new Ticket();
        ticket.setTicketId("ea789c2c-780e-46d8-adf8-37dd3be390ab");
        ticket.setTrainId("12345");
        ticket.setDate(LocalDate.of(2024, 8, 30));

        when(bookingRepository.findByTicketId("ea789c2c-780e-46d8-adf8-37dd3be390ab")).thenReturn(Optional.of(ticket));
        bookingService.cancelTicket("ea789c2c-780e-46d8-adf8-37dd3be390ab");

        verify(bookingRepository, times(1)).findByTicketId("ea789c2c-780e-46d8-adf8-37dd3be390ab");
        verify(bookingRepository, times(1)).delete(ticket);
        verify(inventoryService, times(1)).save(any(Inventory.class));
        verify(trainDetailsService, times(1)).save(any(TrainDetails.class));
    }

    @Test(expected = TicketCancellationException.class)
    public void testCancelTicketTicketNotFound() throws TicketCancellationException {
        when(bookingRepository.findByTicketId("ea789c2c-780e-46d8-adf8-37dd3be390ab")).thenReturn(Optional.empty());
        bookingService.cancelTicket("ea789c2c-780e-46d8-adf8-37dd3be390ab");
    }

    @Test(expected = ExceededCapacityException.class)
    public void testBookTicket_ExceededCapacity() throws ExceededCapacityException {
        Inventory mockInventory = new Inventory();
        mockInventory.setTrainId("12345");
        mockInventory.setDate(LocalDate.of(2024, 8, 30));
        mockInventory.setSeats(100);
        mockInventory.setAvaliableSeats(0);

        when(inventoryService.findByTrainIdAndDate(eq("12345"), any(LocalDate.class))).thenReturn(mockInventory);
        bookingService.bookTicket("Kolkata", "Bangalore", "12345", LocalDate.of(2024, 8, 30), "Abhijit", "Mandal", "abhijit@gmail.com");
    }

    @Test
    public void testBookTicketSuccess() throws ExceededCapacityException {
        Ticket bookedTicket = bookingService.bookTicket("Kolkata", "Bangalore", "12345", LocalDate.of(2024, 8, 30),
                "Abhijit", "Mandal", "abhijit@gmail.com");
        verify(bookingRepository, times(1)).save(any(Ticket.class));
        verify(inventoryService, times(1)).save(any(Inventory.class));
        verify(trainDetailsService, times(1)).save(any(TrainDetails.class));
        assertEquals("12345", bookedTicket.getTrainId());
        assertEquals("HowrahYashwanthpur", bookedTicket.getTrainName());
        assertEquals(LocalDate.of(2024, 7, 30), bookedTicket.getDate());
        assertEquals("Kolkata", bookedTicket.getFromLocation());
        assertEquals("Bangalore", bookedTicket.getToLocation());
        assertEquals("Abhijit", bookedTicket.getFirstName());
        assertEquals("Mandal", bookedTicket.getLastName());
        assertEquals("abhijit@gmail.com", bookedTicket.getEmail());
        assertEquals(1, (int) bookedTicket.getSeatNumber());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testModifySeat_TicketNotFound() throws ResourceNotFoundException {
        when(bookingRepository.findByTicketId("ea789c2c-780e-46d8-adf8-37dd3be390ab")).thenReturn(Optional.empty());
        bookingService.modifySeat("ea789c2c-780e-46d8-adf8-37dd3be390ab", 5);
    }

    @Test
    public void testModifySeatSuccess() throws ResourceNotFoundException {
        Ticket ticket = new Ticket();
        ticket.setTicketId("ea789c2c-780e-46d8-adf8-37dd3be390ab");
        ticket.setTrainId("12345");
        ticket.setDate(LocalDate.of(2024, 8, 30));
        when(bookingRepository.findByTicketId("ea789c2c-780e-46d8-adf8-37dd3be390ab")).thenReturn(Optional.of(ticket));
        Ticket modifiedTicket = bookingService.modifySeat("ea789c2c-780e-46d8-adf8-37dd3be390ab", 10);
        verify(bookingRepository, times(1)).findByTicketId("ea789c2c-780e-46d8-adf8-37dd3be390ab");
        verify(bookingRepository, times(1)).save(any(Ticket.class));
        assertEquals(10, (int) modifiedTicket.getSeatNumber());
    }
}
