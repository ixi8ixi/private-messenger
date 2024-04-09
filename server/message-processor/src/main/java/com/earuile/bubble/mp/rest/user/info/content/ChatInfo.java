package com.earuile.bubble.mp.rest.user.info.content;

import lombok.Builder;

@Builder
public record ChatInfo(
        String id,
        String name,
        long time
) {
}
