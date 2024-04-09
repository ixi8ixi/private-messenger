package com.earuile.bubble.mp.public_interface.chat.create.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CreateChatResponseDto(
        String id,
        LocalDateTime time
) {
}
