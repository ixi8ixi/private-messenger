package com.earuile.bubble.core.rest.dto;

import java.util.List;

public record AddUsersToChatRequest(
        String chatId,
        List<String> userIds
) {}
