package com.earuile.bubble.mp.public_interface.text.content;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record Text(
        String text
) {
}
