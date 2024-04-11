package com.earuile.bubble.mp.public_interface.content.dto;

import lombok.Builder;

import java.time.Instant;
import java.util.List;

@Builder
public record ChatInfoDto(
        String id,
        String name,
        List<UserInfoDto> users,
        Instant time
) {
}
