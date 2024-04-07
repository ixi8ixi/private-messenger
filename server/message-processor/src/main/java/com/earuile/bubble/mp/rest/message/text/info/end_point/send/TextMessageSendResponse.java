package com.earuile.bubble.mp.rest.message.text.info.end_point.send;

import lombok.Builder;

@Builder
public record TextMessageSendResponse(
        String id,
        long time
) {
}
