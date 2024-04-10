package com.earuile.bubble.mp.rest.controller.message.text.info.content;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import lombok.Builder;

/**
 * Abstraction for encoded text message (now without encoding).
 */
@Builder
public record TextMessage(
        @NotBlank String userId,
        String textData,
        @Null String id,
        @Null Long timeDate
) {
}
