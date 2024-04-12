package com.earuile.bubble.mp.public_interface.user.registration.dto;

import lombok.Builder;

import java.time.Instant;

@Builder
public record UserRegistrationResponseDto(
        String userId,
        Instant time
) {
}
