package com.earuile.bubble.mp.public_interface.message.text.dto;

import com.earuile.bubble.mp.public_interface.message.text.content.TextDto;
import lombok.Builder;

@Builder
public record TextSendRequestDto(
        String userId,
        String chatId,
        TextDto textDto
) {
}
