package com.amachi.app.vitalia.config.api;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "openapi")
@Data
public class OpenApiProperties {
    private String title;
    private String version;
    private String description;
    private String termsOfService;
    private License license;
    private Contact contact;

    @Data
    public static class License {
        private String name;
        private String url;
    }

    @Data
    public static class Contact {
        private String name;
        private String email;
        private String url;
    }
}
