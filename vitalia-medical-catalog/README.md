# Vitalia Medical Catalog Module

## Overview
This module is the **Clinical Source of Truth**. It manages the standardized datasets required for medical interoperability, patient safety, and regulatory compliance.

## Key Responsibilities
- **ICD-10 (Diagnosis)**: Management of international classification of diseases.
- **CUPS (Procedures)**: Catalog of medical procedures and services.
- **Vademecum (Medications)**: Massive database of pharmaceuticals and prescriptions.
- **Medical Specialties**: Hierarchy of professional healthcare areas.
- **Generic CRUD**: Uses the `GenericService` pattern for high-performance retrieval and search.

## Table Prefix
Tables in this module use the **`CAT_`** prefix (Catalog).

## Seeding
The data for this module is seeded via Flyway migrations (`V3__` series), ensuring all environments have access to standardized clinical data upon startup.
