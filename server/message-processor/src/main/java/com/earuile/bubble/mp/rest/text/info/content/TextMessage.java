package com.earuile.bubble.mp.rest.text.info.content;

import lombok.Builder;

/**
 * Abstraction for encoded message (now without encoding).
 */
@Builder
public record TextMessage(
        String userId,
        String textData,
        String id,
        Long timeDate
) {
}
