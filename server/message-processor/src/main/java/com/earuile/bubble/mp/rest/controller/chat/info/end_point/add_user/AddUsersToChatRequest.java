package com.earuile.bubble.mp.rest.controller.chat.info.end_point.add_user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
@Builder
public record AddUsersToChatRequest(
        @NotBlank String chatId,
        @NotNull List<@NotBlank String> userIds
) {
}
