package com.earuile.bubble.mp.rest.handler.info;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
public record ValidationErrorResponse(
        @NotNull List<Violation> violations
) {
}
