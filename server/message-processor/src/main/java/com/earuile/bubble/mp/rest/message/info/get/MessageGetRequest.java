package com.earuile.bubble.mp.rest.message.info.get;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record MessageGetRequest(
        @NotBlank String userId,
        @NotBlank String chatId,
        int limit,
        String lastKnownId
) {
}
