# Module Documentation: Tenant Configuration (SuperAdmin)

This document provides a permanent record of the implementation and configuration of the **Tenant Configuration** module.

## 1. Overview
The Tenant Configuration module allows SuperAdmins to manage organizational settings for each tenant, including timezones, locales, user limits, and authentication policies.

## 2. Technical Stack
- **Component Path**: `src/app/platform/pages/tenants/tenant-configs/`
- **CRUD Configuration**: `tenant-configs-crud.config.ts`
- **Utility**: `src/app/shared/utils/i18n-utils.ts` (Centralized Timezone & Locale logic)

## 3. Key Features Correctly Implemented
- **Dynamic Organization Selection**: Filters out `GLOBAL` type tenants to ensure only standard organizations are configured.
- **Standardized Dropdowns**:
    - **Timezones**: Uses `Intl.supportedValuesOf('timeZone')`.
    - **Locales**: Uses a curated list in `i18n-utils.ts` (ES, EN, FR) and planned regional languages (Aymara, Quechua).
- **Material Icons**: Integrated into form inputs for a professional look.

## 4. Pending Backend Logic (Roadmap)
The following fields are preserved in the DB/Entity but require future backend implementation:
- **Max. Users**: Enforcement logic in user creation service.
- **Storage Quota**: Size validation in file upload services.
- **Login Local / Email Verification**: Enforcement in the Authentication/Onboarding flows.
- **Fallback Header**: To be consumed by the UI for dynamic branding.

## 5. i18n Keys Structure
Consistent translation keys added to `es-ES.json`, `en-US.json`, and `fr-FR.json` under the path:
`menu.tenant_governance.tenant_config.*`

---
*Last updated: 2026-03-09*
