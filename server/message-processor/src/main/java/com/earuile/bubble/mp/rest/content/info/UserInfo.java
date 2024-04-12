package com.earuile.bubble.mp.rest.content.info;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record UserInfo(
        @NotBlank String id,
        @NotBlank String login,
        @NotBlank String name,
        long time
) {
}
