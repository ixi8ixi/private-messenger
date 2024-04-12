package com.earuile.bubble.mp.public_interface.message.dto;

import lombok.Builder;

@Builder
public record MessageGetRequestDto(
        String userId,
        String chatId,
        int limit,
        String lastKnownId
) {
}
