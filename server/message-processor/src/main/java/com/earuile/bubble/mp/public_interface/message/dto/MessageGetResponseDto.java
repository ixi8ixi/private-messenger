package com.earuile.bubble.mp.public_interface.message.dto;

import com.earuile.bubble.mp.public_interface.message.content.MessageDto;
import lombok.Builder;

import java.time.Instant;
import java.util.List;

@Builder
public record MessageGetResponseDto(
        String userId,
        String chatId,
        List<MessageDto<Object>> messages,
        Instant time
) {
}
