package com.earuile.bubble.mp.rest.user.info.end_point.chat;

import com.earuile.bubble.mp.rest.user.info.content.ChatInfo;
import lombok.Builder;

import java.util.List;

@Builder
public record UserGetChatsResponse(
        List<ChatInfo> chats,
        long time
) {
}
