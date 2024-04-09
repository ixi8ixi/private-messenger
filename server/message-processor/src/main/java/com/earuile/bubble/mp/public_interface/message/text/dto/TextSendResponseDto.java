package com.earuile.bubble.mp.public_interface.message.text.dto;

import com.earuile.bubble.mp.public_interface.message.MessageDto;
import com.earuile.bubble.mp.public_interface.message.text.content.TextDto;
import lombok.Builder;

@Builder
public record TextSendResponseDto(
        MessageDto<TextDto> messageDto
) {
}
