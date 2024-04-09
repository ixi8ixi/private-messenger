package com.earuile.bubble.rest.dto;

public record UserRegistrationRequest(
        String login,
        String password,
        String name
) {}
