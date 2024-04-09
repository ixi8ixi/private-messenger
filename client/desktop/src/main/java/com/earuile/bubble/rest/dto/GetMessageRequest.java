package com.earuile.bubble.rest.dto;

public record GetMessageRequest(
        String userId,
        String chatId,
        int limit,
        String lastKnownId
) {}
