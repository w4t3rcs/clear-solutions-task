package com.w4t3rcs.task.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("application.user")
public class UserProperties {
    private Integer allowedAge;
}
