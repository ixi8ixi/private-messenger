package com.earuile.bubble.core.rest.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "server.chats")
public record ChatsRestInteractionProperty(
        String createChat,
        String appendUsers,
        String getInfo
) {}
