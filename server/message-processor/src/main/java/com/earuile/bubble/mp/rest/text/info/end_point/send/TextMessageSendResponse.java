package com.earuile.bubble.mp.rest.text.info.end_point.send;

import lombok.Builder;

@Builder
public record TextMessageSendResponse(
    String time
) { }
