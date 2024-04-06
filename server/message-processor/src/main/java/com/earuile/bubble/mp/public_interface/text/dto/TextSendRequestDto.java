package com.earuile.bubble.mp.public_interface.text.dto;

import com.earuile.bubble.mp.public_interface.text.content.Text;
import lombok.Builder;

@Builder
public record TextSendRequestDto(
        String userId,
        String chatId,
        Text text
) {
}
