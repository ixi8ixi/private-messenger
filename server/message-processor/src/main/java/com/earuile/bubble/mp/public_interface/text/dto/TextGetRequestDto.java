package com.earuile.bubble.mp.public_interface.text.dto;

import lombok.Builder;

@Builder
public record TextGetRequestDto(
        String userId,
        String chatId,
        int limit,
        long lastKnownId
) { }
