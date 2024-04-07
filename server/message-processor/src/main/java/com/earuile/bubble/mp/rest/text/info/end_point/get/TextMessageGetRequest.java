package com.earuile.bubble.mp.rest.text.info.end_point.get;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record TextMessageGetRequest(
        @NotBlank String userId,
        @NotBlank String chatId,
        int limit,
        String lastKnownId
) { }
