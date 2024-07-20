package com.assessment.inc.services.impl;

import com.assessment.inc.entites.Inventory;
import com.assessment.inc.entites.Ticket;
import com.assessment.inc.entites.TrainDetails;
import com.assessment.inc.exceptions.ExceededCapacityException;
import com.assessment.inc.exceptions.ResourceNotFoundException;
import com.assessment.inc.exceptions.TicketCancellationException;
import com.assessment.inc.respositories.BookingRepository;
import com.assessment.inc.services.BookingService;
import com.assessment.inc.services.InventoryService;
import com.assessment.inc.services.TrainDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookingServiceImpl implements BookingService {


    private final BookingRepository bookingRepository;
    private final InventoryService inventoryService;
    private final TrainDetailsService trainDetailsService;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository, InventoryService inventoryService, TrainDetailsService trainDetailsService) {
        this.bookingRepository = bookingRepository;
        this.inventoryService = inventoryService;
        this.trainDetailsService=trainDetailsService;
    }


    @Override
    public TrainDetails getTrainById(String trainId) {
        TrainDetails trainDetail = trainDetailsService.getTrainById(trainId);
        return trainDetail;
    }

    @Override
    @Transactional
    public void cancelTicket(String ticketId) throws TicketCancellationException {
        Optional<Ticket> optionalTicket = bookingRepository.findById(ticketId);

        if (!optionalTicket.isPresent()) {
            throw new TicketCancellationException("Ticket with ID " + ticketId + " not found");
        }
        Ticket ticket = optionalTicket.get();
        Inventory inventory = inventoryService.getInventoryById(ticket.getTrainId());
        TrainDetails trainDetail = trainDetailsService.getTrainById(ticket.getTrainId());
        bookingRepository.delete(ticket);


        inventory.setAvaliableSeats(inventory.getAvaliableSeats() + 1);
        inventoryService.save(inventory);

        trainDetail.setAvaliableSeats(trainDetail.getAvaliableSeats() + 1);
        trainDetailsService.save(trainDetail);
    }

    @Override
    @Transactional
    public Ticket bookTicket(String fromLocation, String toLocation, String trainId, String firstName, String lastName, String emailAddress) throws ExceededCapacityException {

        // Fetch train details
        Inventory inventory = inventoryService.getInventoryById(trainId);
        TrainDetails trainDetail = trainDetailsService.getTrainById(trainId);
        if (inventory == null) {
            throw new IllegalArgumentException("Train with ID " + trainId + " not found");
        }


        if (inventory.getSeats() <= 0 || inventory.getAvaliableSeats() <= 0) {
            throw new ExceededCapacityException("Train " + trainId + " is fully booked");
        }

        // Create a new Ticket object
        Ticket ticket = new Ticket();
        ticket.setTicketId(String.valueOf(UUID.randomUUID()));
        ticket.setTrainId(trainId);
        ticket.setTrainName(inventory.getTrainName());
        ticket.setDate(LocalDate.now());
        ticket.setFromLocation(fromLocation);
        ticket.setToLocation(toLocation);
        ticket.setFirstName(firstName);
        ticket.setLastName(lastName);
        ticket.setEmail(emailAddress);
        ticket.setSeatNumber(getNextAvailableSeatNumber(inventory));
        ticket.setSection(getSectionForSeat(ticket.getSeatNumber(), inventory));
        ticket.setFare(inventory.getFare());
        // Save the Ticket object
        bookingRepository.save(ticket);

        // Adjust available seats (assuming this is part of business logic)
        inventory.setAvaliableSeats(inventory.getAvaliableSeats() - 1);
        inventoryService.save(inventory);

        trainDetail.setAvaliableSeats(trainDetail.getAvaliableSeats() - 1);
        trainDetailsService.save(trainDetail);

        return ticket;

    }
    private String getSectionForSeat(int seatNumber, Inventory inventory) {
        return (seatNumber <= inventory.getSeats() / 2) ? "A" : "B";
    }
    private int getNextAvailableSeatNumber(Inventory inventory) {
        int nextAvailableSeatNumber = inventory.getSeats() - inventory.getAvaliableSeats()+ 1;
        return nextAvailableSeatNumber;
    }
    private boolean isCapacityExceeded(String trainId) {
        Inventory inventorydetails=  inventoryService.getInventoryById(trainId);
        int avaliableSeats=inventorydetails.getAvaliableSeats();
        if(avaliableSeats<1){
            return true;
        }
        return false;
    }

    @Override
    public Ticket getTicket(String ticketId) {
        Optional<Ticket> optionalTicket = bookingRepository.findById(ticketId);
        return optionalTicket.orElse(null);
    }

    @Override
    public List<Ticket> getTicketsByEmail(String emailAddress) {
        return bookingRepository.findByEmail(emailAddress);
    }


    @Override
    @Transactional
    public Ticket modifySeat(String ticketId, int newSeatNumber) {
        Optional<Ticket> optionalTicket = bookingRepository.findByTicketId(ticketId);
        if (optionalTicket.isPresent()) {
            Ticket ticket = optionalTicket.get();
            ticket.setSeatNumber(newSeatNumber);
            return bookingRepository.save(ticket);
        } else {
            throw new ResourceNotFoundException("Ticket with ID " + ticketId + " not found");
        }
    }

}
