package com.earuile.bubble.core.rest.dto;

import java.util.List;

public record UserGetChatsResponse(
        List<ChatInfo> chats,
        Long time
) {}
