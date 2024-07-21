package com.assessment.inc.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TrainEntityDto {
    private String trainId;
    private String trainName;
    private LocalDate date;
    private String fromLocation;
    private String toLocation;
    private Integer seats;
    private Double fare;
    public TrainEntityDto() {
    }

    public TrainEntityDto(String trainId, String trainName, LocalDate date, String fromLocation, String toLocation, Integer seats, Double fare) {
        this.trainId = trainId;
        this.trainName = trainName;
        this.date = date;
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
        this.seats = seats;
        this.fare = fare;
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



    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    public Double getFare() {
        return fare;
    }

    public void setFare(Double fare) {
        this.fare = fare;
    }


    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
