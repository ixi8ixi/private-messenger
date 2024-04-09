package com.earuile.bubble.mp.public_interface.user.chat;

import lombok.Builder;

@Builder
public record UserGetChatsRequestDto(
        String userId
) {
}
