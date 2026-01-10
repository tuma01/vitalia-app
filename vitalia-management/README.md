# Vitalia Management Module

## Overview
This module acts as the "Governance Layer" of the application. It handles the administrative aspects of multi-tenancy, platform-wide configuration, and institutional settings.

## Key Responsibilities
- **Tenant Administration**: Management of Clinic/Hospital organizations (`Tenants`).
- **User Governance**: Creation and management of `SuperAdmin` and `TenantAdmin` users.
- **Platform Configuration**: Global settings, bootstrap logic, and feature flags.
- **Organization Themes**: Customization and branding settings for individual tenants (e.g., color schemes, logos).

## Table Prefix
Tables in this module use the **`MGT_`** prefix (Management).

## Structure
- `superadmin`: Logic restricted to global platform administrators.
- `tenantadmin`: Logic for administrators of a specific clinic/tenant.
- `config`: Modules for system-wide configuration and bootstrap processes.

## Usage
This module is a consumer of the `core-*` series and provides the necessary administrative interfaces for the backend.
```xml
<dependency>
    <groupId>com.amachi.app.vitalia</groupId>
    <artifactId>vitalia-management</artifactId>
</dependency>
```
