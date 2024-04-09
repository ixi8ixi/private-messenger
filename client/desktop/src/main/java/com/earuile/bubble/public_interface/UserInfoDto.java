package com.earuile.bubble.public_interface;

public record UserInfoDto(
        String id,
        String login,
        String name,
        String password
) {}
