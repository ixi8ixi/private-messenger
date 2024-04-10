package com.earuile.bubble.mp.rest.controller.chat.info.end_point.chat_info;

import com.earuile.bubble.mp.rest.content.UserInfo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
public record GetChatInfoResponse(
        @NotBlank String chatId,
        @NotBlank String name,
        @NotNull List<UserInfo> users,
        long time
) {
}
