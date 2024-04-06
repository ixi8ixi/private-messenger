package com.earuile.bubble.mp.public_interface.text.content;

import lombok.Builder;

@Builder
public record Text(
        String text,
        String time
) {
}
