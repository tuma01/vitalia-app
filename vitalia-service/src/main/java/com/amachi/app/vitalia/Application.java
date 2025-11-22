package com.amachi.app.vitalia;

import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

// TODO por el momento desactivamos spring securities
//@SpringBootApplication(
//        exclude = {
////                SecurityAutoConfiguration.class,
////                UserDetailsServiceAutoConfiguration.class,
//                // Excluir la seguridad de Actuator para evitar el error
//                ManagementWebSecurityAutoConfiguration.class
//        }
//)
//@SpringBootApplication
@SpringBootApplication(
//        scanBasePackages = {
//                "com.amachi.app.vitalia.common",
//                "com.amachi.app.vitalia.service",
//                "com.amachi.app.vitalia.authentication",
//                "com.amachi.app.vitalia.geography"
//        },
        exclude = { ManagementWebSecurityAutoConfiguration.class }
)
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@Slf4j
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public ApplicationRunner flywayInfo(Flyway flyway) {
        return args -> {
            var current = flyway.info().current();
            if (current != null) {
                log.info("Flyway current version: {}", current.getVersion());
            } else {
                log.info("No Flyway migrations applied yet.");
            }
        };
    }
}