package com.earuile.bubble.mp.rest.controller.user.info.end_point.user_info;

import jakarta.validation.constraints.Null;
import lombok.Builder;

@Builder
public record GetUserInfoRequest(
        @Null String userId,
        @Null String login
) {
}
