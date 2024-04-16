package com.earuile.bubble.core.util;

public class LocalizedMessageException extends RuntimeException {
    public LocalizedMessageException(String message) {
        super(message);
    }

    /**
     * TODO: to be implemented...
     */
    public String getFormattedMessage() {
        return getMessage();
    }
}
