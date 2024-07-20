package com.assessment.inc.entites;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Inventory")
@IdClass(TrainKey.class)
public class Inventory {

    @Id
    private String trainId;
    private String trainName;
    @Id
    private String fromLocation;

    @Id
    private String toLocation;

    @Id
    private LocalDate date;

    // Other fields
    private Integer seats;
    private Integer avaliableSeats;
    private Double fare;
    public Integer getAvaliableSeats() {
        return avaliableSeats;
    }

    public void setAvaliableSeats(Integer avaliableSeats) {
        this.avaliableSeats = avaliableSeats;
    }



    public Double getFare() {
        return fare;
    }

    public void setFare(Double fare) {
        this.fare = fare;
    }

    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public String getTrainId() {
        return trainId;
    }

    public void setTrainId(String trainId) {
        this.trainId = trainId;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }



    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    // Constructors, getters, setters, equals, and hashCode methods
}

