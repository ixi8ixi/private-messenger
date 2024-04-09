package com.earuile.bubble.mp.rest.exception;

public class ValidationException extends IllegalArgumentException {

    public ValidationException(String message, String expected, String actual) {
        super("%s Expected: %s. Actual: %s.".formatted(message, expected, actual));
    }

}
