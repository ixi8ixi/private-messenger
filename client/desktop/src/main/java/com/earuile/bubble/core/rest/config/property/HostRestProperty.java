package com.earuile.bubble.core.rest.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "server")
public record HostRestProperty(String host) {
}
