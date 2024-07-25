package com.assessment.inc.exceptions;

public class PastDateException extends RuntimeException{

    public PastDateException(String s) {
        super(s);
    }

    public PastDateException(){
        super("Train cannot be booked for past date");
    }
}
