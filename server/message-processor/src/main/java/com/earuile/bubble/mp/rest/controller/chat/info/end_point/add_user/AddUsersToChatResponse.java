package com.earuile.bubble.mp.rest.controller.chat.info.end_point.add_user;

import com.earuile.bubble.mp.rest.content.info.ChatInfo;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record AddUsersToChatResponse(
        @NotNull ChatInfo chatInfo,
        long time
) {
}
