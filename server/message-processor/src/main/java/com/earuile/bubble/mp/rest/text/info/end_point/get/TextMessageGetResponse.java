package com.earuile.bubble.mp.rest.text.info.end_point.get;

import com.earuile.bubble.mp.rest.text.info.content.TextMessage;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.util.List;

@Builder
public record TextMessageGetResponse(
        @NotBlank String chatId,
        List<TextMessage> textMessages,
        long time
) {
}
