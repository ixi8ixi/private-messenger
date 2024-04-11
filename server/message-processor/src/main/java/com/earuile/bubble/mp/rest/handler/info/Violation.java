package com.earuile.bubble.mp.rest.handler.info;

import lombok.Builder;

@Builder
public record Violation(
        String fieldName,
        String message
) {
}
