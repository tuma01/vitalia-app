package com.amachi.app.vitalia.medicalcatalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.amachi.app.vitalia")
public class AppMedicalCatalog {
    public static void main(String[] args) {
        SpringApplication.run(AppMedicalCatalog.class, args);
    }
}
