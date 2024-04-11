package com.earuile.bubble.core.rest.dto;

public record UserInfo(
        String id,
        String login,
        String name,
        Long time
) {}
