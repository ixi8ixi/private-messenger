package com.earuile.bubble.core.rest.config;

import com.earuile.bubble.core.rest.config.property.ChatsRestInteractionProperty;
import com.earuile.bubble.core.rest.config.property.HostRestProperty;
import com.earuile.bubble.core.rest.config.property.MessagesRestInteractionProperty;
import com.earuile.bubble.core.rest.config.property.UsersRestInteractionProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableScheduling
@EnableConfigurationProperties({
        HostRestProperty.class,
        UsersRestInteractionProperty.class,
        ChatsRestInteractionProperty.class,
        MessagesRestInteractionProperty.class
})
public class MessengerServerRestConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate(new HttpComponentsClientHttpRequestFactory());
    }
}
