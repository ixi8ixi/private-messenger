package com.earuile.bubble.public_interface.user;

public record RegisterFormDto(
        String login,
        String password,
        String name
) {}
