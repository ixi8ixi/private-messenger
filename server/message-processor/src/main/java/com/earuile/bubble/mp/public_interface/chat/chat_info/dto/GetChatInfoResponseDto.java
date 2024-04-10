package com.earuile.bubble.mp.public_interface.chat.chat_info.dto;

import com.earuile.bubble.mp.public_interface.content.dto.ChatInfoDto;
import lombok.Builder;

@Builder
public record GetChatInfoResponseDto(
        ChatInfoDto chatInfoDto
) {
}
