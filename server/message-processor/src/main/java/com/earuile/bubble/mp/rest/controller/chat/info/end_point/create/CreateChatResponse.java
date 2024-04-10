package com.earuile.bubble.mp.rest.controller.chat.info.end_point.create;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CreateChatResponse(
        @NotBlank String id,
        long time
) {
}
