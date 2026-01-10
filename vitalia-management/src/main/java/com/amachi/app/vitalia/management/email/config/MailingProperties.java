package com.amachi.app.vitalia.management.email.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "mailing")
@Data
public class MailingProperties {
    private Frontend frontend;

    @Data
    public static class Frontend {
        private String baseUrl;
        private String activationPath;
    }
}