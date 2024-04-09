package com.earuile.bubble.mp.rest.user.info.end_point.register;

import lombok.Builder;

@Builder
public record UserRegistrationResponse(
        String userId,
        long time
) {
}
