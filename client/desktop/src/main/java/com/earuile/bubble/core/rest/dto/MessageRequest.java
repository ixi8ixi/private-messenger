package com.earuile.bubble.core.rest.dto;

public record MessageRequest(
        String chatId,
        TextMessage textMessage
) {}
