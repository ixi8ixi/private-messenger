package com.earuile.bubble.core.rest.dto;

import java.util.List;

public record GetMessageResponse(
    String chatId,
    List<TextMessage> textMessages,
    long time
) {}
