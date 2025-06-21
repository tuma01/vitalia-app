package com.amachi.app.vitalia.config.api;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class OpenAPIConfig {
    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Value("${server.port}")
    private String serverPort;


    @Value("${spring.application.name}")
    private String appName;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("OpenAPI specification - " + appName)
                        .version("1.0")
                        .description("This API exposes endpoints to manage Vitalia App")
                        .termsOfService("https://www.vitalia.com/terms")
                        .license(new License().name("MIT License").url("https://www.vitalia.com/license"))
                        .contact(new Contact()
                                .name("Juan Amachi")
                                .email("juan.amachi@gmail.com")
                                .url("https://www.amachi.com")
                        )
                )
                .servers(Collections.singletonList(
                        new io.swagger.v3.oas.models.servers.Server()
                                .url("http://localhost:" + serverPort + contextPath)
                                .description("Server URL in Development environment")
                ));
    }
//    private final RequestMappingHandlerMapping handlerMapping;
//    public OpenAPIConfig(RequestMappingHandlerMapping handlerMapping) {
//        this.handlerMapping = handlerMapping;
//    }
//
//    @Bean
//    public OpenApiCustomizer customOperationId() {
//        return openApi -> {
//            Map<RequestMappingInfo, HandlerMethod> handlerMethods = handlerMapping.getHandlerMethods();
//
//            for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
//                HandlerMethod handlerMethod = entry.getValue();
//                if (handlerMethod.getBeanType().getSuperclass().equals(BaseController.class)) {
//                    // Obtenemos el nombre de la entidad llamando al método getEntityName()
//                    try {
//                        String entityName = (String) handlerMethod.getBeanType()
//                                .getMethod("getEntityName")
//                                .invoke(handlerMethod.getBean());
//
//                        // Modificamos operationId en OpenAPI
//                        for (Map.Entry<String, PathItem> pathEntry : openApi.getPaths().entrySet()) {
//                            PathItem pathItem = pathEntry.getValue();
//                            if (pathItem.getGet() != null) {
//                                Operation operation = pathItem.getGet();
//                                if ("defaultFindById".equals(operation.getOperationId())) {
//                                    operation.setOperationId(entityName + "FindById");
//                                }
//                            }
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        };
//    }


}

//
//@OpenAPIDefinition(
//        info = @Info(
//                contact = @Contact(
//                        name = "Juan Amachi",
//                        email = "juan.amachi@gmail.com",
//                        url = "www.amachi.com"
//                ),
//                description = "This API exposes endpoints to manage Hospital App",
//                title = "OpenAPI specification - Hospital App",
//                version = "1.0",
//                license = @License(
//                        name = "MIT License",
//                        url = "https://www.hospital.com/terms"
//                ),
//                termsOfService = "Terms of service"
//        ),
//        servers = {
//                @Server(
//                        description = "Server URL in Development environment",
//                        url = "${server.servlet.context-path}"
////                        url = "http://localhost:8088/api/v1"
//                ),
//                @Server(
//                        description = "Server URL in Production environment",
//                        url = "https://hospital-app.com/"
//                )
//        })