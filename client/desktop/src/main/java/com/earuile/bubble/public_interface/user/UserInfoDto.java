package com.earuile.bubble.public_interface.user;

public record UserInfoDto(
        String id,
        String login,
        String name,
        Long time
) {}
