package com.earuile.bubble.mp.rest.controller.user.info.end_point.user_info;

import lombok.Builder;

@Builder
public record GetUserInfoRequest(
        String userId,
        String login
) {
}
