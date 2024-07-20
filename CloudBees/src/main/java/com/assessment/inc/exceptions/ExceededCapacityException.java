package com.assessment.inc.exceptions;

public class ExceededCapacityException  extends RuntimeException {
    public ExceededCapacityException(String s) {
        super(s);
    }

    public ExceededCapacityException(){
        super("Train capacity exceeded !!");
    }
}
