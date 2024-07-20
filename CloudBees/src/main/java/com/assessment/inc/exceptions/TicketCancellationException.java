package com.assessment.inc.exceptions;

public class TicketCancellationException extends RuntimeException {
    public TicketCancellationException(String message) {
        super(message);
    }
}