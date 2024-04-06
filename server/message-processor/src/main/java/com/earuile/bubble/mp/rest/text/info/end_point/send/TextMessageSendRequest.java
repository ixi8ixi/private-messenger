package com.earuile.bubble.mp.rest.text.info.end_point.send;

import com.earuile.bubble.mp.rest.text.info.content.TextMessage;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record TextMessageSendRequest(
    @NotBlank String userId,
    @NotBlank String chatId,
    TextMessage textMessage
) { }
