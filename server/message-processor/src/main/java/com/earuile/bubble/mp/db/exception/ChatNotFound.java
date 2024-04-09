package com.earuile.bubble.mp.db.exception;

import java.util.NoSuchElementException;

public class ChatNotFound extends NoSuchElementException {

    public ChatNotFound(String message) {
        super(message);
    }

}
