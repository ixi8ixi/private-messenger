package com.earuile.bubble.mp.rest.user.registration.info.end_point.register;

import lombok.Builder;

@Builder
public record UserRegistrationResponse(
        String userId,
        long time
) {
}
