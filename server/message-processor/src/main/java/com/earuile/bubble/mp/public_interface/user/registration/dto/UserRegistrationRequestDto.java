package com.earuile.bubble.mp.public_interface.user.registration.dto;

import lombok.Builder;

@Builder
public record UserRegistrationRequestDto(
        String login,
        String password,
        String name
) {
}
