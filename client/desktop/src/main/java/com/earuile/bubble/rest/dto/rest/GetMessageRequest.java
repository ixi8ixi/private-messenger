package com.earuile.bubble.rest.dto.rest;

public record GetMessageRequest(
        String userId,
        String chatId,
        int limit,
        String lastKnownId
) {}
