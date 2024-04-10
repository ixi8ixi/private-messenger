package com.earuile.bubble.mp.rest.controller.message.info.end_point.get;

import com.earuile.bubble.mp.rest.controller.message.text.info.content.TextMessage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
public record MessageGetResponse(
        @NotBlank String chatId,
        @NotNull List<TextMessage> textMessages,
        long time
) {
}
