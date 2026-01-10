# Vitalia Boot Module

## Overview
This is the **Orchestration and Entry Point** of the Vitalia application. It is the only executable module and serves as the bridge between all other modules.

## Key Responsibilities
- **Application Runner**: Contains `VitaliaApplication.java`, the main class that starts the Spring context.
- **Resource Hub**: Centralizes all configuration files (`application.yml`, `bootstrap.yml`) and internationalization properties (`messages.properties`).
- **Global Configuration & Handlers**:
    - **OpenAPI / Swagger**: Centralized `OpenApiConfig` to document endpoints from all business modules (`management`, `clinical`, `catalog`).
    - **Global Exception Handling**: Centralized `GlobalExceptionHandler` to provide a uniform error response format across the entire platform.
- **Database Migrations**: Houses the Flyway scripts that build and update the schema across all modules.
- **Aggregation**: Defines the parent-child relationships in the `pom.xml` to include all required domain and core modules.

## Usage
To run the application locally:
```bash
mvn spring-boot:run -pl vitalia-boot
```

## Structure
- `src/main/java`: Global configurations and Spring Boot entry point.
- `src/main/resources`: i18n, configs, and Flyway SQL scripts.
