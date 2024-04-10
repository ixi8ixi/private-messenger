package com.earuile.bubble.mp.rest.controller.user.info.end_point.chat;

import com.earuile.bubble.mp.rest.content.info.ChatInfo;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
public record UserGetChatsResponse(
        @NotNull List<ChatInfo> chats,
        long time
) {
}
