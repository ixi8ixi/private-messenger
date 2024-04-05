package com.earuile.bubble.mp.rest.message.info;

import lombok.Builder;

@Builder
public record MessageResponse(
    String message
) { }
