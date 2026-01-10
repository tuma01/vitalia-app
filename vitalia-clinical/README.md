# Vitalia Clinical Module

## Overview
The `vitalia-clinical` module is where the healthcare business happens. This module was created during Phase 2 of the 2026 refactoring to isolate medical-specific entities and logic from administrative ones.

## Key Responsibilities
- **Patient Records**: Full lifecycle management of Patients, including their clinical evolution and demographic links.
- **Medical Staff (Employees)**: Manages profiles for Doctors, Nurses, and clinical support staff.
- **Physical Traits (Avatar)**: Handles localized data about physical characteristics and personal identity markers (Avatars).
- **Medical Domain Logic**: Implementation of complex business rules for healthcare delivery.

## Table Prefix
Tables in this module use the **`CLN_`** prefix (Clinical).

## Structure
- `entity`: Clinically focused entities (`Patient`, `Employee`, `Avatar`).
- `service`: Medical business logic and orchestration.
- `repository`: JPA interfaces for CLN tables.
- `mapper`: Data Transfer Object mappings for medical records.

## Usage
This module depends on `core-domain` for shared entities like `Person`.
```xml
<dependency>
    <groupId>com.amachi.app.vitalia</groupId>
    <artifactId>vitalia-clinical</artifactId>
</dependency>
```
