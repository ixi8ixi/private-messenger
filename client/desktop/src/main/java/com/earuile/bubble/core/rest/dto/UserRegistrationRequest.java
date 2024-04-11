package com.earuile.bubble.core.rest.dto;

public record UserRegistrationRequest(
        String login,
        String password,
        String name
) {}
