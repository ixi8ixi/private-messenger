package com.earuile.bubble.mp.rest.controller.user.info.end_point.user_info;

import com.earuile.bubble.mp.rest.content.info.UserInfo;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record GetUserInfoResponse(
        @NotNull UserInfo userInfo,
        long time
) {
}
