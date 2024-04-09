package com.earuile.bubble.mp.rest.chat.info.end_point.create;

import lombok.Builder;

@Builder
public record CreateChatResponse(
        String id,
        long time
) {
}
