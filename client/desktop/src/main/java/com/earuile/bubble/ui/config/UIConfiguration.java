package com.earuile.bubble.ui.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({ SceneStyleProperty.class })
public class UIConfiguration {
}
