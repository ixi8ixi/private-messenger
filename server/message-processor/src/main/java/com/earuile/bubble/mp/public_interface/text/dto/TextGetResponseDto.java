package com.earuile.bubble.mp.public_interface.text.dto;

import com.earuile.bubble.mp.public_interface.text.content.Text;
import lombok.Builder;

import java.util.List;

@Builder
public record TextGetResponseDto(
        String userId,
        String chatId,
        List<Text> texts,
        String time
) {
}
