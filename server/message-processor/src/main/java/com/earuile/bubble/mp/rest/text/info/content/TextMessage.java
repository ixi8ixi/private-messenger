package com.earuile.bubble.mp.rest.text.info.content;

import lombok.Builder;

/**
 * Abstraction for encoded message (now without encoding).
 */
@Builder
public record TextMessage(
        String textData,
        String timeData
) {
}
