package com.earuile.bubble.mp.rest.controller.user.info.end_point.register;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record UserRegistrationRequest(
        @NotBlank String login,
        @NotBlank String password,
        @NotBlank String name
) {
}
