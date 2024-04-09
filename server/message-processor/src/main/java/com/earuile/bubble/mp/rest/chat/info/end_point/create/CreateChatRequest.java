package com.earuile.bubble.mp.rest.chat.info.end_point.create;

import lombok.Builder;

import java.util.List;

@Builder
public record CreateChatRequest(
        String creatorUserId,
        String name,
        List<String> userIds
) {
}
