package com.earuile.bubble.mp.public_interface.content;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record ChatInfoDto(
        String id,
        String name,
        List<UserInfoDto> users,
        LocalDateTime time
) {
}
