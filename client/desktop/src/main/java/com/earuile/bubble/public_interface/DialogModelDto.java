package com.earuile.bubble.public_interface;

public record DialogModelDto(
        String name,
        String lastMessage,
        String time,
        String chatId
) {}
