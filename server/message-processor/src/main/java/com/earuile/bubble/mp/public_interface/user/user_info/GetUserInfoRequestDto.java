package com.earuile.bubble.mp.public_interface.user.user_info;

import lombok.Builder;

@Builder
public record GetUserInfoRequestDto(
        String userId,
        String login
) {
}
