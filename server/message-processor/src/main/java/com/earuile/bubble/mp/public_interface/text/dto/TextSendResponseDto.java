package com.earuile.bubble.mp.public_interface.text.dto;

import com.earuile.bubble.mp.public_interface.Message;
import com.earuile.bubble.mp.public_interface.text.content.Text;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record TextSendResponseDto(
        Message<Text> message
) {
}
