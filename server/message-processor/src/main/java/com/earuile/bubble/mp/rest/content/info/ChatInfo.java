package com.earuile.bubble.mp.rest.content.info;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import lombok.Builder;

import java.util.List;

@Builder
public record ChatInfo(
        @NotBlank String id,
        @NotBlank String name,
        @Null List<UserInfo> users,
        long time
) {
}
