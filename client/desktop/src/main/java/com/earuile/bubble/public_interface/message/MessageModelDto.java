package com.earuile.bubble.public_interface.message;

// todo move to local dto package in ui chat

public record MessageModelDto(
       String username,
       String text,
       String messageId,
       String time
) {}
