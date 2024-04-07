package com.earuile.bubble.mp.rest.message.text.info.content;

import lombok.Builder;

/**
 * Abstraction for encoded text message (now without encoding).
 */
@Builder
public record TextMessage(
        String userId,
        String textData,
        String id,
        Long timeDate
) {
}
