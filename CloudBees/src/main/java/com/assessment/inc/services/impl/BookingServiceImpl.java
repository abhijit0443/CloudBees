package com.assessment.inc.services.impl;

import com.assessment.inc.entites.Inventory;
import com.assessment.inc.entites.Ticket;
import com.assessment.inc.entites.TrainDetails;
import com.assessment.inc.exceptions.ExceededCapacityException;
import com.assessment.inc.exceptions.PastDateException;
import com.assessment.inc.exceptions.ResourceNotFoundException;
import com.assessment.inc.exceptions.TicketCancellationException;
import com.assessment.inc.respositories.BookingRepository;
import com.assessment.inc.services.BookingService;
import com.assessment.inc.services.InventoryService;
import com.assessment.inc.services.TrainDetailsService;
import com.assessment.inc.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookingServiceImpl implements BookingService {

    private static final Logger logger = LoggerFactory.getLogger(BookingServiceImpl.class);

    private final BookingRepository bookingRepository;
    private final InventoryService inventoryService;
    private final TrainDetailsService trainDetailsService;
    private final UserService userService;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository, InventoryService inventoryService, TrainDetailsService trainDetailsService,UserService userService) {
        this.bookingRepository = bookingRepository;
        this.inventoryService = inventoryService;
        this.trainDetailsService = trainDetailsService;
        this.userService=userService;
    }

    @Override
    public List<TrainDetails> findByTrainId(String trainId) {
        logger.info("Finding train details for train ID: {}", trainId);
        List<TrainDetails> trainDetail = trainDetailsService.findByTrainId(trainId);
        return trainDetail;
    }

    @Override
    @Transactional
    public void cancelTicket(String ticketId) throws TicketCancellationException {
        logger.info("Cancelling ticket with ID: {}", ticketId);
        Optional<Ticket> optionalTicket = bookingRepository.findByTicketId(ticketId);

        if (!optionalTicket.isPresent()) {
            throw new TicketCancellationException("Ticket with ID " + ticketId + " not found");
        }
        Ticket ticket = optionalTicket.get();
        Inventory inventory = inventoryService.findByTrainIdAndDate(ticket.getTrainId(), ticket.getDate());
        TrainDetails trainDetail = trainDetailsService.findByTrainIdAndDate(ticket.getTrainId(), ticket.getDate());
        bookingRepository.delete(ticket);

        inventory.setAvaliableSeats(inventory.getAvaliableSeats() + 1);
        inventoryService.save(inventory);

        trainDetail.setAvailableSeats(trainDetail.getAvailableSeats() + 1);
        trainDetailsService.save(trainDetail);

        logger.info("Ticket with ID {} has been cancelled successfully", ticketId);
    }

    @Override
    @Transactional
    public Ticket bookTicket(String fromLocation, String toLocation, String trainId, LocalDate date, String firstName, String lastName, String emailAddress) throws ExceededCapacityException {
        logger.info("Booking ticket for train ID: {} from {} to {} on {}", trainId, fromLocation, toLocation, date);
        if (date.isBefore(LocalDate.now())) {
            throw new PastDateException(String.format("Train cannot be booked for past date: %s", date));
        }

        Inventory inventory = inventoryService.findByTrainIdAndDate(trainId, date);
        TrainDetails trainDetail = trainDetailsService.findByTrainIdAndDate(trainId, date);

        if (inventory == null) {
            logger.error("Train with ID {} not found", trainId);
            throw new ResourceNotFoundException(String.format("Train with ID %s not found", trainId));
        }

        if (inventory.getSeats() <= 0 || inventory.getAvaliableSeats() <= 0) {
            logger.error("Train {} is fully booked", trainId);
            throw new ExceededCapacityException("Train " + trainId + " is fully booked");
        }

        Ticket ticket = getTicket( firstName, lastName, emailAddress, inventory);
        bookingRepository.save(ticket);

        inventory.setAvaliableSeats(inventory.getAvaliableSeats() - 1);
        inventoryService.save(inventory);

        trainDetail.setAvailableSeats(trainDetail.getAvailableSeats() - 1);
        trainDetailsService.save(trainDetail);

        logger.info("Ticket booked successfully with ID: {}", ticket.getTicketId());
        return ticket;
    }

    private Ticket getTicket( String firstName, String lastName, String emailAddress, Inventory inventory) {
        Ticket ticket = new Ticket();
        ticket.setTicketId(String.valueOf(UUID.randomUUID()));
        ticket.setTrainId(inventory.getTrainId());
        ticket.setTrainName(inventory.getTrainName());
        ticket.setDate(inventory.getDate());
        ticket.setFromLocation(inventory.getFromLocation());
        ticket.setToLocation(inventory.getToLocation());
        ticket.setFirstName(firstName);
        ticket.setLastName(lastName);
        ticket.setEmail(emailAddress);
        ticket.setSeatNumber(getNextAvailableSeatNumber(inventory));
        ticket.setSection(getSectionForSeat(ticket.getSeatNumber(), inventory));
        ticket.setFare(inventory.getFare());
        return ticket;
    }

    private String getSectionForSeat(int seatNumber, Inventory inventory) {
        return (seatNumber <= inventory.getSeats() / 2) ? "A" : "B";
    }

    private int getNextAvailableSeatNumber(Inventory inventory) {
        int nextAvailableSeatNumber = inventory.getSeats() - inventory.getAvaliableSeats() + 1;
        return nextAvailableSeatNumber;
    }

    @Override
    public Ticket getTicket(String ticketId) {
        logger.info("Fetching ticket details for ticket ID: {}", ticketId);
        Optional<Ticket> optionalTicket = bookingRepository.findByTicketId(ticketId);
        if (optionalTicket.isPresent()) {
            return optionalTicket.get();
        } else {
            logger.error("Ticket with ID {} not found", ticketId);
            throw new ResourceNotFoundException("Ticket with ID " + ticketId + " not found");
        }
    }

    @Override
    public List<Ticket> getTicketsByEmail(String emailAddress) {
        logger.info("Fetching tickets for email address: {}", emailAddress);
        List<Ticket>  tickets= bookingRepository.findByEmail(emailAddress);
        if (tickets.size()>0) {
            return tickets;
        }else{
            logger.error("Ticket with Email id  {} not found", emailAddress);
            throw new ResourceNotFoundException("Ticket with Email id " + emailAddress + " not found");
        }
    }

    @Override
    @Transactional
    public Ticket modifySeat(String ticketId, int newSeatNumber) {
        logger.info("Modifying seat for ticket ID: {} to new seat number: {}", ticketId, newSeatNumber);
        Optional<Ticket> optionalTicket = bookingRepository.findByTicketId(ticketId);
        if (optionalTicket.isPresent()) {
            Ticket ticket = optionalTicket.get();
            ticket.setSeatNumber(newSeatNumber);
            return bookingRepository.save(ticket);
        } else {
            logger.error("Ticket with ID {} not found", ticketId);
            throw new ResourceNotFoundException("Ticket with ID " + ticketId + " not found");
        }
    }

    @Override
    public  List<Ticket> getUserDetailsBySecAndByTrainId(String section, String trainId) {
        List<Ticket>  tickets= bookingRepository.findBySectionAndTrainId(section,trainId);
        if (tickets.size()>0) {
            /*for(int index =0;index<tickets.size();index++) {
                userService.getCustomerByEmail(tickets.get(index).getEmail());
            }*/
            return tickets;
        }else{
            logger.error("User details with section {} and trainId {} not found", section,trainId);
            throw new ResourceNotFoundException("User details with section "+section+" and "+trainId +"not found");
        }
    }

  /*  public List<Ticket> getAllBookingsForUser(String email) {
        return bookingRepository.findAllBookingsForUser(email);
    }*/
}
