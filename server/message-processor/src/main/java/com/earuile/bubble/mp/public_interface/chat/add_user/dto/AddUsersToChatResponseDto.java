package com.earuile.bubble.mp.public_interface.chat.add_user.dto;

import com.earuile.bubble.mp.public_interface.content.dto.ChatInfoDto;
import lombok.Builder;

@Builder
public record AddUsersToChatResponseDto(
        ChatInfoDto chatInfo
) {
}
