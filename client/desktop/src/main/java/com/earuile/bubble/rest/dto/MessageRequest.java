package com.earuile.bubble.rest.dto;

public record MessageRequest(
        String chatId,
        TextMessage textMessage
) {}
