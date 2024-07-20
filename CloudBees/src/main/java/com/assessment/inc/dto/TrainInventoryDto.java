package com.assessment.inc.dto;

import java.time.LocalDate;

public class TrainInventoryDto {
    private String trainId;
    private String trainName;
    private LocalDate date;
    private String fromLocation;
    private String toLocation;
    private Integer seats;
    private Integer avaliableSeats;

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

    public Integer getAvaliableSeats() {
        return avaliableSeats;
    }

    public void setAvaliableSeats(Integer avaliableSeats) {
        this.avaliableSeats = avaliableSeats;
    }


}
