package com.assessment.inc.util;

public class Constants {

    // Error messages
    public static final String USER_ID_NOT_BLANK = "User ID must not be blank";
    public static final String USER_FAILED_TO_REMOVE = "Failed to remove user";
    public static final String TRAIN_SEAT_CAPACITY_EXCEED = "Train capacity exceeded";

    public static final String VALIDATE_ERROR="Validation error";
    public static final String DATABASE_ERROR="Database error";
    public static final String UNEXPECTED_ERROR="Unexpected error";
    public static final String TICKET_ID_NOT_BLANK = "Ticket ID must not be blank";
    public static final String TICKET_CANCELLED_SUCCESS = "Ticket with ID %s has been canceled";

    // Success messages
    public static final String USER_REMOVED_SUCCESS = "User with ID %s has been removed";
    public static final String USER_CREATED_SUCCESS = "User created successfully";
    public static final String FROM_LOCATION_NOT_BLANK = "From location must not be blank";
    public static final String TO_LOCATION_NOT_BLANK = "To location must not be blank";


    public static final String TRAIN_ID_REQUIRED = "Train ID is required";
    public static final String FROM_LOCATION_REQUIRED = "From location is required";
    public static final String TO_LOCATION_REQUIRED = "To location is required";
    public static final String DATE_REQUIRED = "Date is required";
    public static final String FIRST_NAME_REQUIRED = "First name is required";
    public static final String LAST_NAME_REQUIRED = "Last name is required";
    public static final String EMAIL_REQUIRED = "Email address is required";

    public static final String API_BASE_PATH = "/api/v1/trains";
    public static final String API_TRAIN_BY_ID = "/trainById/{trainId}";
    public static final String API_TRAIN_BY_TICKET_ID = "/ticket/{ticketId}";
    public static final String API_BOOK_TICKET = "/book";
    public static final String API_CANCEL_TICKET = "/cancel/{ticketId}";
    public static final String API_MODIFY_SEAT = "/modify-seat/{ticketId}";

    public static final String API_CREATE_TRAINS = "/createTrains";
    public static final String API_GET_TRAINS_BY_FROM_TO_LOCATIONS = "/from/{fromLocation}/to/{toLocation}";
    public static final String API_FIND_TRAIN_ID = "/{trainId}";
    public static final String API_DELETE_TRAIN_ID = "/{trainId}";



}
