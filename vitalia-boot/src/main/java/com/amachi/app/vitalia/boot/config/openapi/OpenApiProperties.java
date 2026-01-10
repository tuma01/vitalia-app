package com.amachi.app.vitalia.boot.config.openapi;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "openapi")
public class OpenApiProperties {

    private String title;
    private String version;
    private String description;
    private String termsOfService;
    private License license = new License();
    private Contact contact = new Contact();

    @Data
    public static class License {
        private String name = "Apache 2.0";
        private String url = "https://www.apache.org/licenses/LICENSE-2.0.html";
    }

    @Data
    public static class Contact {
        private String name = "Support Team";
        private String email = "support@tuempresa.com";
        private String url = "https://tuempresa.com";
    }
}
