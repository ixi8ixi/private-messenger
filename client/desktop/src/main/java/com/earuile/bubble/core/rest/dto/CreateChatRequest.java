package com.earuile.bubble.core.rest.dto;

import java.util.List;

public record CreateChatRequest(
        String creatorUserId,
        String name,
        List<String> userIds
) {}
