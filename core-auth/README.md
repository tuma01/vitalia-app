# Core Auth Module

## Overview
The `core-auth` module is the security heartbeat of Vitalia. It implements a robust Identity and Access Management (IAM) system based on Spring Security, designed for multi-tenant environments.

## Key Responsibilities
- **Authentication Flow**: Implements JWT-based authentication with Refresh Token support to maintain secure, stateless sessions.
- **Tenant Resolution**: Includes the `TenantResolver` which extracts the tenant identity from request headers or security context, feeding the multi-tenancy AOP filters.
- **RBAC (Role-Based Access Control)**: Manages permissions and roles (`SUPER_ADMIN`, `ADMIN`, `DOCTOR`, etc.) across different tenant contexts.
- **Security Configuration**: Defines the global security filter chain, CORS policies, and password encoding strategies.

## Table Prefix
Tables in this module use the **`SEC_`** prefix (Security).

## Usage
Essential for the `vitalia-boot` module and any microservice or module that requires authentication or authorization checks.
```xml
<dependency>
    <groupId>com.amachi.app.core</groupId>
    <artifactId>core-auth</artifactId>
</dependency>
```
