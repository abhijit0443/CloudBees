package com.assessment.inc.dto;

import com.assessment.inc.util.Constants;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.time.LocalDate;



public class BookingRequest {
    @NotBlank(message = Constants.TRAIN_ID_REQUIRED)
    private String trainId;

    @NotBlank(message = Constants.FROM_LOCATION_REQUIRED)
    private String fromLocation;

    @NotBlank(message = Constants.TO_LOCATION_REQUIRED)
    private String toLocation;

    @NotNull(message = Constants.DATE_REQUIRED)
    private LocalDate date;

    @NotBlank(message = Constants.FIRST_NAME_REQUIRED)
    private String firstName;

    @NotBlank(message = Constants.LAST_NAME_REQUIRED)
    private String lastName;

    @NotBlank(message = Constants.EMAIL_REQUIRED)
    private String emailAddress;

    public String getTrainId() {
        return trainId;
    }

    public void setTrainId(String trainId) {
        this.trainId = trainId;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }


    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
