# Vitalia Test Module

## Overview
A specialized infrastructure module designed to enhance the Quality Assurance process. It provides reusable tools and base classes for unit and integration testing.

## Key Responsibilities
- **Mock Data Factories**: Reusable builders for creating test entities (Tenants, Users, Patients).
- **Test Containers Configuration**: Standardized setups for integration tests using Docker containers.
- **Base Test Classes**: Parent classes that pre-configure the Spring context for faster and more reliable testing.

## Usage
Include this module with the `test` scope in any module that requires robust unit or integration testing.
```xml
<dependency>
    <groupId>com.amachi.app.vitalia</groupId>
    <artifactId>vitalia-test</artifactId>
    <scope>test</scope>
</dependency>
```
