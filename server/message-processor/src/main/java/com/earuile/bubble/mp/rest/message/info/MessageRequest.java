package com.earuile.bubble.mp.rest.message.info;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record MessageRequest(
    @NotBlank String message
) { }
