package com.earuile.bubble.mp.rest.controller.message.text.info.content;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

/**
 * Abstraction for encoded text message (now without encoding).
 */
@Builder
public record TextMessage(
        @NotBlank String userId,
        String textData,
        String id,
        Long timeDate
) {
}
