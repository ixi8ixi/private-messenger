package com.earuile.bubble.mp.public_interface.chat.create.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record CreateChatRequestDto(
        String creatorUserId,
        String name,
        List<String> userIds
) {
}
