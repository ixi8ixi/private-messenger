package com.earuile.bubble.mp.rest.controller.chat.info.end_point.chat_info;

import com.earuile.bubble.mp.rest.content.info.ChatInfo;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record GetChatInfoResponse(
        @NotNull ChatInfo chatInfo,
        long time
) {
}
