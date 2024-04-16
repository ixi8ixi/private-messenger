package com.earuile.bubble.core.rest.dto;

import java.util.List;

public record MessageGetResponse(
        String chatId,
        List<TextMessage> textMessages,
        Long time
) {}
