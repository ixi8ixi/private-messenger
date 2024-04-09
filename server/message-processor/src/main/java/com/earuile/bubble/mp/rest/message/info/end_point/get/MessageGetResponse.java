package com.earuile.bubble.mp.rest.message.info.end_point.get;

import com.earuile.bubble.mp.rest.message.text.info.content.TextMessage;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.util.List;

@Builder
public record MessageGetResponse(
        @NotBlank String chatId,
        List<TextMessage> textMessages,
        long time
) {
}
