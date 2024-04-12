package com.earuile.bubble.mp.public_interface.user.chat.dto;

import lombok.Builder;

@Builder
public record UserGetChatsRequestDto(
        String userId
) {
}
