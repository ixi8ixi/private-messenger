package com.earuile.bubble.public_interface;

public record SendMessageDto(
        String chatId,
        String userId,
        String text
) {}
