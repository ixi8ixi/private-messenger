package com.earuile.bubble.mp.public_interface.user.chat.dto;

import com.earuile.bubble.mp.public_interface.content.ChatInfoDto;
import lombok.Builder;

import java.util.List;

@Builder
public record UserGetChatsResponseDto(
        List<ChatInfoDto> chats
) {
}
