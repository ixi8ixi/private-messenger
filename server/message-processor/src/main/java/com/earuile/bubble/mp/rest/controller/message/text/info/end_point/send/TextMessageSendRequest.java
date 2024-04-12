package com.earuile.bubble.mp.rest.controller.message.text.info.end_point.send;

import com.earuile.bubble.mp.rest.controller.message.text.info.content.TextMessage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record TextMessageSendRequest(
        @NotBlank String chatId,
        @NotNull TextMessage textMessage
) {
}
