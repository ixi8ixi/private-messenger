package com.earuile.bubble.core.rest.dto;

public record GetMessageRequest(
        String userId,
        String chatId,
        int limit,
        String lastKnownId
) {}
