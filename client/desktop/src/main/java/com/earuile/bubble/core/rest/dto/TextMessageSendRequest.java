package com.earuile.bubble.core.rest.dto;

public record TextMessageSendRequest(
        String chatId,
        TextMessage textMessage
) {}
