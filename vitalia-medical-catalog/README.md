# Vitalia Medical Catalog Module

## Overview
The **Vitalia Medical Catalog** module is the **Clinical Source of Truth** for the Vitalia application. It manages standardized datasets and medical master data (MDM) required for clinical interoperability, patient safety, and regulatory compliance.

This module has been fully refactored to follow a **Professional and Homogeneous Architectural Pattern**, ensuring consistency across all clinical and demographic master data components.

## Architectural Pattern: Homogeneous & Professional
All sub-modules within this catalog adhere to a strict architectural contract:

### 1. API Interface (Contract First)
Each module defines its contract in a dedicated Java Interface (e.g., `AllergyApi`).
- **OpenAPI/Swagger Documentation**: All endpoint metadata, descriptions, and response codes are defined in the interface.
- **REST Logic**: Spring MVC annotations (`@GetMapping`, `@PostMapping`, etc.) are kept in the interface to maintain clean implementations.
- **Null Safety**: Parameter constraints are enforced via `@NonNull` and `@Valid`.

### 2. Standardized Controllers
Controllers (e.g., `AllergyController`) implement their respective API interfaces.
- **Clean Implementation**: No redundant Swagger or REST annotations inside the controller body.
- **Logic Separation**: Controllers focus on delegating work to services and mapping entities to DTOs.
- **Generic CRUD**: Every controller inherits from `BaseController` and implements standard operations.

### 3. Service Layer (Robustness & Consistency)
Services (e.g., `AllergyServiceImpl`) implement standard business logic:
- **Null Safety**: Extensive use of `requireNonNull` and `@NonNull` to prevent `NullPointerException`.
- **Simplified Operations**: Common patterns for search, pagination, and relational lookups.
- **Unified Exceptions**: Consistent use of `ResourceNotFoundException` with standardized i18n keys.

---

## Managed Modules
The catalog currently manages the following 12 master data modules:

| Category | Module | Description | API Path |
| :--- | :--- | :--- | :--- |
| **Clinical** | **Allergy** | Clinical types of allergies and sensitivities. | `/mdm/allergy` |
| | **BloodType** | Standardized blood groups (A+, O-, etc.). | `/mdm/blood-type` |
| | **Medication** | Pharmaceutical vademecum (Active ingredients). | `/mdm/medication` |
| | **Diagnosis (Icd10)** | International Classification of Diseases (CIM-10). | `/mdm/diagnosis` |
| | **MedicalProcedure** | Catalog of medical procedures (CUPS). | `/mdm/procedure` |
| | **Vaccine** | Standardized immunization catalog. | `/mdm/vaccine` |
| **Healthcare** | **MedicalSpecialty** | Professional healthcare areas and specialties. | `/mdm/medical-specialty` |
| | **HealthcareProvider** | Entities providing healthcare services (IPS). | `/mdm/healthcare-provider` |
| **Identity** | **IdentificationType** | Valid ID types (CC, TI, Passport, etc.). | `/mdm/identity` |
| | **Kinship** | Relationship types for emergency contacts. | `/mdm/kinship` |
| **Demographic**| **CivilStatus** | Marital/Civil status categorization. | `/mdm/demographic/civil-status` |
| | **Gender** | Biological and social gender classification. | `/mdm/demographic/gender` |

---

## Technical Features

### Modern Tooling
- **Spring Boot**: Underlying framework for RESTful services.
- **Hibernate / JPA**: Data persistence and specification-based search.
- **MapStruct**: High-performance object mapping between Entities and DTOs.
- **Lombok**: Boilerplate reduction for DTOs and entities.

### Advanced Search & Pagination
Each module supports advanced filtering via **JPA Specifications** and paginated results using standard index/size parameters, returning the `PageResponseDto` structure.

### Data Seeding
Standardized clinical data is seeded via **Flyway Migrations** (`V3__` series), ensuring that all environments (Dev, QA, Prod) operate on the same clinical dataset.

## Table Prefix
Tables in this module use the **`CAT_`** prefix, identifying them as part of the Global Catalog system.
