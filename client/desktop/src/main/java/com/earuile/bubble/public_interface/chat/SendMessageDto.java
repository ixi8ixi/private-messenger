package com.earuile.bubble.public_interface.chat;

public record SendMessageDto(
        String chatId,
        String userId,
        String text
) {}
