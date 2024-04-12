package com.earuile.bubble.mp.rest.controller.chat.info.end_point.chat_info;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record GetChatInfoRequest(
        @NotBlank String chatId
) {
}
