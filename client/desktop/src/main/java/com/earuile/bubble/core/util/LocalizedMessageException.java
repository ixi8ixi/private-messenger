package com.earuile.bubble.core.util;

public class LocalizedMessageException extends RuntimeException {
    private final String message;

    public LocalizedMessageException(String message) {
        super();
        this.message = message;
    }

    /**
     * TODO: to be implemented...
     */
    public String getFormattedMessage() {
        return message;
    }
}
