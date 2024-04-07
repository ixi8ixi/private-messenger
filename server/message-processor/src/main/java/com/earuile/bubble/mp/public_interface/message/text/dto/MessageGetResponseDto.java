package com.earuile.bubble.mp.public_interface.message.text.dto;

import com.earuile.bubble.mp.public_interface.message.MessageDto;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record MessageGetResponseDto(
        String userId,
        String chatId,
        List<MessageDto<Object>> messages,
        LocalDateTime time
) {
}
