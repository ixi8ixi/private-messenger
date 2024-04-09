package com.earuile.bubble.mp.db.exception;

import java.util.NoSuchElementException;

public class UserNotFound extends NoSuchElementException {

    public UserNotFound(String message) {
        super(message);
    }

}
