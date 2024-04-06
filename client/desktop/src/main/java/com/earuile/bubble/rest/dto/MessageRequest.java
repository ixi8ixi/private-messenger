package com.earuile.bubble.rest.dto;

public record MessageRequest(
        String userId,
        String chatId,
        TextMessage textMessage
) {}
