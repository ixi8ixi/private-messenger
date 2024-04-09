package com.earuile.bubble.mp.rest.user.validation;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "rest.user.validation")
public class UserValidation {
    public String idPrefix;
    public int maxLengthLogin;
    public int maxLengthPassword;
    public int maxLengthName;

}
