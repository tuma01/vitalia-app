# Core Common Module

## Overview
This module serves as the primary infrastructure layer for the entire Vitalia application. It contains domain-agnostic utilities, base classes, and shared logic that provide a consistent foundation for all other modules.

## Key Responsibilities
- **Base Entities**: Provides `BaseEntity` and `Auditable` classes to standardize ID generation and audit tracking (created at, updated at).
- **Generic Service Pattern**: Implements the `GenericService` and `GenericServiceImpl` patterns to reduce boilerplate code for CRUD operations.
- **Multi-Tenancy AOP**: Contains the `TenantFilterAspect` and `@TenantFilter` annotation logic, ensuring that data isolation is enforced at the Hibernate level via Aspect-Oriented Programming.
- **Shared Exceptions**: Defines a centralized hierarchy of runtime exceptions (e.g., `ResourceNotFoundException`, `BusinessException`).
- **Utilities**: Houses common utility classes and constants (`AppConstants`, `DateUtils`).

## Table Prefix
Tables defined in this module (if any) or related to common infrastructure use the **`COM_`** prefix.

## Usage
Add this module as a dependency to any module that requires base infrastructure or needs to respect multi-tenant data isolation.
```xml
<dependency>
    <groupId>com.amachi.app.core</groupId>
    <artifactId>core-common</artifactId>
</dependency>
```
