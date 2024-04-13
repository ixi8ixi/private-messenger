package com.earuile.bubble.public_interface;

public record UserDataDto(
        String id,
        String login,
        String name,
        String password
) {}
