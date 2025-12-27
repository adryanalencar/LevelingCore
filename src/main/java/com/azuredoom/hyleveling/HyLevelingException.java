package com.azuredoom.hyleveling;

public class HyLevelingException extends RuntimeException {

    public HyLevelingException(String failedToCloseH2Connection, Exception e) {
        super(failedToCloseH2Connection, e);
    }

    public HyLevelingException(String failedToCloseH2Connection) {
        super(failedToCloseH2Connection);
    }
}
