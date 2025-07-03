package com.amachi.app.vitalia.config.api;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@RequiredArgsConstructor
@Configuration
public class OpenApiConfig {

    private final OpenApiProperties openApiProperties;

    @Value("${server.servlet.context-path:/}")
    private String contextPath;

    @Value("${server.port:8088}")
    private String serverPort;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(openApiProperties.getTitle())
                        .version(openApiProperties.getVersion())
                        .description(openApiProperties.getDescription())
                        .termsOfService(openApiProperties.getTermsOfService())
                        .license(new License()
                                .name(openApiProperties.getLicense().getName())
                                .url(openApiProperties.getLicense().getUrl()))
                        .contact(new Contact()
                                .name(openApiProperties.getContact().getName())
                                .email(openApiProperties.getContact().getEmail())
                                .url(openApiProperties.getContact().getUrl()))
                )
                .servers(Collections.singletonList(
                        new io.swagger.v3.oas.models.servers.Server()
                                .url("http://localhost:" + serverPort + contextPath)
                                .description("Server URL in Development environment")
                ));
    }
}
