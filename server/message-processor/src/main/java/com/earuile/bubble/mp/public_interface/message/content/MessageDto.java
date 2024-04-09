package com.earuile.bubble.mp.public_interface.message.content;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record MessageDto<T>(
        T content,
        String id,
        String userId,
        LocalDateTime time
) {
}
