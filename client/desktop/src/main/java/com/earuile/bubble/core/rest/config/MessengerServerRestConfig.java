package com.earuile.bubble.core.rest.config;

import com.earuile.bubble.core.rest.config.property.ChatsRestInteractionProperty;
import com.earuile.bubble.core.rest.config.property.HostRestProperty;
import com.earuile.bubble.core.rest.config.property.MessagesRestInteractionProperty;
import com.earuile.bubble.core.rest.config.property.UsersRestInteractionProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
@EnableScheduling
@EnableConfigurationProperties({
        HostRestProperty.class,
        UsersRestInteractionProperty.class,
        ChatsRestInteractionProperty.class,
        MessagesRestInteractionProperty.class
})
@RequiredArgsConstructor
public class MessengerServerRestConfig {
    private final HostRestProperty hostRestProperty;

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(hostRestProperty.host()));
        return restTemplate;
    }
}
