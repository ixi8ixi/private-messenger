package com.earuile.bubble.mp.rest.controller.chat.info.end_point.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import lombok.Builder;

import java.util.List;

@Builder
public record CreateChatRequest(
        @NotBlank String creatorUserId,
        @NotBlank String name,
        @Null List<String> userIds
) {
}
