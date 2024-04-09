package com.earuile.bubble.mp.rest.chat.validation;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "rest.chat.validation")
public class ChatValidation {
    public String idPrefix;
    public int maxLengthName;

}
