package com.earuile.bubble.mp.rest.controller.user.info.end_point.chat;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record UserGetChatsRequest(
        @NotBlank String userId
) {
}
