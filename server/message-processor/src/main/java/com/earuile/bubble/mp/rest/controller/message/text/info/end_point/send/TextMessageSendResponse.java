package com.earuile.bubble.mp.rest.controller.message.text.info.end_point.send;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record TextMessageSendResponse(
        @NotBlank String id,
        long time
) {
}
