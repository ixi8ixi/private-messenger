package com.earuile.bubble.mp.public_interface.chat.chat_info;

import com.earuile.bubble.mp.public_interface.content.UserInfoDto;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record GetChatInfoResponseDto(
        String chatId,
        String name,
        List<UserInfoDto> users,
        LocalDateTime time
) {
}
