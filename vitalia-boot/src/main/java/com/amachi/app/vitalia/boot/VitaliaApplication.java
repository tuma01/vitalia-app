package com.amachi.app.vitalia.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.amachi.app")
@EntityScan(basePackages = "com.amachi.app")
@EnableJpaRepositories(basePackages = "com.amachi.app")
public class VitaliaApplication {

    public static void main(String[] args) {
        SpringApplication.run(VitaliaApplication.class, args);
    }
}
