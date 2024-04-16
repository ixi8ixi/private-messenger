package com.earuile.bubble.core.rest.dto;

public record MessageGetRequest(
        String userId,
        String chatId,
        Integer limit,
        String lastKnownId
) {}
