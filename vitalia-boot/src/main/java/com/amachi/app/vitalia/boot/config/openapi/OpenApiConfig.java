package com.amachi.app.vitalia.boot.config.openapi;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class OpenApiConfig {

        private final OpenApiProperties openApiProperties;

        @Value("${server.servlet.context-path:/}")
        private String contextPath;

        @Value("${server.port:8080}")
        private String serverPort;

        @Bean
        public OpenAPI customOpenAPI() {
                String serverUrl = "http://localhost:" + serverPort + contextPath;
                log.info("🌐 Configurando OpenAPI para servidor en: {}", serverUrl);

                return new OpenAPI()
                                .info(buildInfo())
                                .servers(List.of(
                                                new Server()
                                                                .url(serverUrl)
                                                                .description("Development Server")));
        }

        private Info buildInfo() {
                var license = openApiProperties.getLicense();
                var contact = openApiProperties.getContact();

                return new Info()
                                .title(openApiProperties.getTitle())
                                .version(openApiProperties.getVersion())
                                .description(openApiProperties.getDescription())
                                .termsOfService(openApiProperties.getTermsOfService())
                                .license(new License()
                                                .name(license.getName())
                                                .url(license.getUrl()))
                                .contact(new Contact()
                                                .name(contact.getName())
                                                .email(contact.getEmail())
                                                .url(contact.getUrl()));
        }
}
