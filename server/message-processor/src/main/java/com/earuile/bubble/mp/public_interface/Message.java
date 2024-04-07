package com.earuile.bubble.mp.public_interface;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record Message<T>(
        T content,
        String id,
        String userId,
        LocalDateTime time
) {
}
