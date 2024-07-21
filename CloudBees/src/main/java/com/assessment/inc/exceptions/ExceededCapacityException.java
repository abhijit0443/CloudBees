package com.assessment.inc.exceptions;

import com.assessment.inc.util.Constants;

public class ExceededCapacityException  extends RuntimeException {
    public ExceededCapacityException(String s) {
        super(s);
    }

    public ExceededCapacityException(){
        super(Constants.TRAIN_SEAT_CAPACITY_EXCEED);
    }
}
