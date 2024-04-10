package com.earuile.bubble.mp.public_interface.chat.chat_info;

import lombok.Builder;

@Builder
public record GetChatInfoRequestDto(
        String chatId
) {
}
