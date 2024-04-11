package com.earuile.bubble.mp.public_interface.chat.add_user.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record AddUsersToChatRequestDto(
        String chatId,
        List<String> userIds
) {
}
