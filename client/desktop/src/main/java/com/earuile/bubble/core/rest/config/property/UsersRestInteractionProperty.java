package com.earuile.bubble.core.rest.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "server.users")
public record UsersRestInteractionProperty(
        String createNewUser,
        String allUserChats,
        String getInfo
) {}
