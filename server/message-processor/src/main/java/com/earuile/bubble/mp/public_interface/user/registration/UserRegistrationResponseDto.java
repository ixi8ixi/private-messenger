package com.earuile.bubble.mp.public_interface.user.registration;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UserRegistrationResponseDto(
        String userId,
        LocalDateTime time
) {
}
