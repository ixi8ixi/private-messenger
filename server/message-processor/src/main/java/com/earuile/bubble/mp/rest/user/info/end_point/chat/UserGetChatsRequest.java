package com.earuile.bubble.mp.rest.user.info.end_point.chat;

import lombok.Builder;

@Builder
public record UserGetChatsRequest(
        String userId
) {
}
