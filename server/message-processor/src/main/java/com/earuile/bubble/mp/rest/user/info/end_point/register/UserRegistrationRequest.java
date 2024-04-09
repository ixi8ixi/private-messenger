package com.earuile.bubble.mp.rest.user.info.end_point.register;

import lombok.Builder;

@Builder
public record UserRegistrationRequest(
        String login,
        String password,
        String name
) {
}
