package com.earuile.bubble.public_interface.chat;

import com.earuile.bubble.core.rest.dto.UserInfo;
import com.earuile.bubble.public_interface.user.UserInfoDto;

import java.util.List;

public record ChatInfoDto(
        String id,
        String name,
        List<UserInfoDto> users,
        Long time
) {}
