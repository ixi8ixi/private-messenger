package com.earuile.bubble.rest.dto.rest;

public record MessageRequest(
        String chatId,
        TextMessage textMessage
) {}
