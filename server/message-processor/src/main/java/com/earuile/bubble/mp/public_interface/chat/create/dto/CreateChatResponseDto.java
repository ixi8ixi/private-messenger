package com.earuile.bubble.mp.public_interface.chat.create.dto;

import lombok.Builder;

import java.time.Instant;

@Builder
public record CreateChatResponseDto(
        String id,
        Instant time
) {
}
