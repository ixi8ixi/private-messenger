package com.earuile.bubble.mp.rest.controller.message.info.end_point.get;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record MessageGetRequest(
        String userId,
        @NotBlank String chatId,
        int limit,
        String lastKnownId
) {
}
