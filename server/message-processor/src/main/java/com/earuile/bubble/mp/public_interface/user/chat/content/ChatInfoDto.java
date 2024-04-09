package com.earuile.bubble.mp.public_interface.user.chat.content;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ChatInfoDto(
        String id,
        String name,
        LocalDateTime time
) {
}
