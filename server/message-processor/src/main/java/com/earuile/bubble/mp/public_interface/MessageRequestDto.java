package com.earuile.bubble.mp.public_interface;

import lombok.Builder;

@Builder
public record MessageRequestDto(
        String message
) { }
