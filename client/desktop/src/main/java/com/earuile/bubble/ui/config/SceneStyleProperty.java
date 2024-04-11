package com.earuile.bubble.ui.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "style")
public record SceneStyleProperty(List<String> include) {}
