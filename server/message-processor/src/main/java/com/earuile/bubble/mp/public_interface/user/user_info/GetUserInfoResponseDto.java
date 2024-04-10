package com.earuile.bubble.mp.public_interface.user.user_info;

import com.earuile.bubble.mp.public_interface.content.dto.UserInfoDto;
import lombok.Builder;

@Builder
public record GetUserInfoResponseDto(
        UserInfoDto userInfo
) {
}
