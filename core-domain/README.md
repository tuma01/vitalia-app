# Core Domain Module

## Overview
The `core-domain` module is the "Source of Truth" for the application's most critical and shared entities. It centralizes the data models that are required by multiple business domains (Authentication, Management, Clinical).

## Key Responsibilities
- **Centralized Entities**: Houses the three pillars of the system identity:
    - **`Person`**: Represents a physical human being. *Note: In this refactored architecture, Person is a concrete entity.*
    - **`Tenant`**: Represents an organization or clinic in the multi-tenant environment.
    - **`User`**: Represents the system credentials and identity linked to a Person.
- **Relational Mapping**: Manages the complex relationships between Users, Persons, and Tenants (e.g., `UserTenantRole`).
- **Domain Mappers**: Provides shared MapStruct mappers for core entities.

## Table Prefixes
- **`MGT_`**: Used for Tenant-related administration tables.
- **`SEC_`**: Used for Security and User-related tables.

## Usage
This module should be included in any module that needs to reference the core actors of the system (Persons, Users, or Tenants).
```xml
<dependency>
    <groupId>com.amachi.app.core</groupId>
    <artifactId>core-domain</artifactId>
</dependency>
```
