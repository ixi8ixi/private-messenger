package com.earuile.bubble.mp.rest.controller.message.info.end_point.get;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import lombok.Builder;

@Builder
public record MessageGetRequest(
        @Null String userId,
        @NotBlank String chatId,
        int limit,
        @Null String lastKnownId
) {
}
