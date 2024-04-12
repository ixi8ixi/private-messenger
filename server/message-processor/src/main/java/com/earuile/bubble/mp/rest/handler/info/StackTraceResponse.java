package com.earuile.bubble.mp.rest.handler.info;

import lombok.Builder;

import java.util.Arrays;
import java.util.List;

@Builder
public record StackTraceResponse(
        String message,
        List<String> stackTrace
) {

    public StackTraceResponse(Exception e, String addingMessage) {
        this(
                "%s %s".formatted(addingMessage, e.getMessage()),
                Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).toList()
        );
    }

}
