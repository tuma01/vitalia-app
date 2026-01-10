# Vitalia Backend Architecture

## 🛡️ Security & Multi-Tenancy

### Zero Trust Data Isolation
The application implements a strict **Zero Trust** data isolation strategy to prevent cross-tenant data leakage.

#### 1. Database-Level Enforcement (Hibernate Filters)
*   Entities like `Person` (which can be shared across tenants) are protected by a Hibernate `@Filter`.
*   **The Rule:** A Person is only visible if a record exists in the `PERSON_TENANT` table linking them to the *Current Tenant*.
*   **Default Behavior:** If no Tenant is selected (e.g. valid global login but no context), the filter defaults to `tenantId = -1`, ensuring **NO DATA** is returned.

#### 2. Automatic Enforcement (AOP)
*   **`TenantFilterAspect`**: Automatically intercepts every `@Transactional` method or Service execution using an `@Around` advice to ensure cleanup.
*   **Lifecycle:** Enables filter -> Executes Method -> Disables filter (finally block).
*   **Zero Trust:** If Context is empty using `tenantId = -1`.
*   **Security Guarantee:** Developers do NOT need to manually add `WHERE tenant_id = ?`. The isolation is transparent.
*   **🚨 ARCHITECTURE RULE:** Developers are **STRICTLY FORBIDDEN** from calling `session.disableFilter("tenantFilter")` in Services or Repositories. Filter lifecycle is exclusive to the Infrastructure Layer.

#### 3. Role Protection (Last Man Standing)
*   **SuperAdmin**: Cannot delete the last remaining SuperAdmin.
*   **TenantAdmin**: Cannot delete the last remaining Administrator of a Tenant (prevents lockout).

### Authentication
*   **JWT & Refresh Token:** Standard secure flow.
*   **Tenant Scoped:** Login is always contextual to a Tenant (or Global for SuperAdmin).
*   **Tenant Soft Delete:** Deleting a tenant (`delete()`) performs a Soft Delete (`isActive = false`). The Authentication service strictly blocks login for any user belonging to an inactive tenant.

## 🏗️ Modular Architecture

Vitalia is built on a highly modular architecture that ensures scalability, strict separation of concerns, and reusable clinical components across multiple tenants.

### 🧩 Core Modules (Foundation)
These modules provide the base infrastructure and centralized domain models used by the entire system.

| Module | Responsibility | Trigram | Key Features |
| :--- | :--- | :--- | :--- |
| **`core-common`** | Shared Utils | `CMN_` | Base entities, generic services, AOP for multi-tenancy, common exceptions. |
| **`core-geography`** | Geo-Context | `GEO_` | Global master data for Countries, Regions, and Cities. |
| **`core-domain`** | Centralized Entities | `DMN_` | Centralized models for `Person`, `Tenant`, and `Theme`. `Person` is a concrete entity here. |
| **`core-auth`** | Security Layer | `AUT_` | Identity management, JWT/Refresh Token, RBAC, and security configuration. |

### 🏥 Vitalia Domain Modules (Application)
These modules contain the business-specific logic for the healthcare domain and administrative management.

| Module | Responsibility | Trigram | Key Features |
| :--- | :--- | :--- | :--- |
| **`vitalia-management`** | Tenant Governance | `MGT_` | SuperAdmin and TenantAdmin logic, organization settings, and tenant-specific users. |
| **`vitalia-medical-catalog`** | Clinical Truth | `CAT_` | Master Data Management (ICD-10, CUPS, Medications, Specialties). |
| **`vitalia-clinical`** | Medical Domain | `CLN_` | Patient records, Medical Employees, Clinicians, and Avatar/Physical traits. |
| **`vitalia-boot`** | Application Runner | - | Main entry point (`VitaliaApplication`), Flyway migrations, and global configuration. |
| **`vitalia-test`** | Quality Assurance | - | Shared test builders, mock data, and integration testing base classes. |

---

## 🛠️ Module Details & Functionality / Detalles y Funcionalidad de los Módulos

### 🧩 Base Modules (Foundation) / Módulos Base (Infraestructura)
The `core-*` modules form the skeleton of the application, providing infrastructure that is domain-agnostic or centrally managed.
*(Los módulos `core-*` forman el esqueleto de la aplicación, proporcionando infraestructura agnóstica al dominio o gestionada centralmente.)*

*   **`core-common`**: Contains base classes (`BaseEntity`, `Auditable`), generic services, and security-related AOP. The `@TenantFilter` is defined here to enforce multi-tenancy at the data layer.
    *(Contiene clases base, servicios genéricos y AOP de seguridad. Aquí se define el filtro de multi-tenencia `@TenantFilter`.)*
*   **`core-domain`**: The "Shared Truth" of the system. It houses entities that are cross-cutting, such as `Person`, `Tenant`, and `User`. 
    *(La "Verdad Compartida". Aloja entidades transversales como `Person`, `Tenant` y `User`.)*
    *   **Architecture Change**: `Person` is now a **concrete entity** to support general identity management across the platform.
        *(Cambio Arquitectónico: `Person` ahora es una **entidad concreta**.)*
*   **`core-auth`**: Handles all authentication logic using Spring Security and JWT.
    *(Gestiona toda la lógica de autenticación usando Spring Security y JWT.)*
*   **`core-geography`**: A standardized module for geographic data (Countries, States, Cities), using the `GEO_` prefix.
    *(Módulo estándar para datos geográficos, usando el prefijo `GEO_`.)*

### 🏥 Domain Modules (Business Logic) / Módulos de Dominio (Lógica de Negocio)
The `vitalia-*` modules implement the specific healthcare and administrative functionality.
*(Los módulos `vitalia-*` implementan la funcionalidad específica de salud y administración.)*

*   **`vitalia-clinical`**: Encapsulates the medical business logic.
    *(Encapsula la lógica de negocio médica.)*
    *   **Focus**: Manages `Patient` records, `Employee` profiles, and `Avatar` traits.
        *(Gestión de registros de Pacientes, perfiles de Empleados y rasgos de Avatar.)*
    *   **Table Prefix**: `CLN_` (Clinical).
*   **`vitalia-management`**: Focused on the governance of the platform.
    *(Enfocado en el gobierno de la plataforma.)*
    *   **Focus**: `SuperAdmin` operations and `TenantAdmin` operations (theme settings).
        *(Operaciones de SuperAdmin y TenantAdmin.)*
    *   **Table Prefix**: `MGT_` (Management).
*   **`vitalia-medical-catalog`**: The clinical source of truth.
    *(La fuente de verdad clínica.)*
    *   **Focus**: Standardized medical data like ICD-10 codes, CUPS, and medication lists.
        *(Datos médicos estandarizados como CIE-10, CUPS y vademécum.)*
    *   **Table Prefix**: `CAT_` (Catalog).
*   **`vitalia-boot`**: The orchestration layer.
    *(La capa de orquestación.)*
    *   **Focus**: Contains the Spring Boot main class and Flyway migrations.
        *(Contiene la clase principal de Spring Boot y las migraciones de Flyway.)*

---

## 📐 Data Standardization

### Table Naming Convention
To avoid table name collisions and improve database organization, every module uses a specific prefix:
*   **`CMN_`**: Common Infrastructure (Audit logs, Shared tables) - `core-common`
*   **`GEO_`**: Geography (Country, City) - `core-geography`
*   **`DMN_`**: Domain Models (Tenant, Theme, Person) - `core-domain`
*   **`AUT_`**: Authentication & Security (UserAccount, Tokens) - `core-auth`
*   **`MGT_`**: Management (SuperAdmin, TenantAdmin) - `vitalia-management`
*   **`CAT_`**: Medical Catalogs (Medicines, Procedures) - `vitalia-medical-catalog`
*   **`CLN_`**: Clinical Records (Patient, Employee) - `vitalia-clinical`

### 🔄 Flyway Migration Order
The database modules must be initialized in the following order to respect foreign key constraints:
1. `V1__` -> `CMN`
2. `V2__` -> `GEO`
3. `V3__` -> `DMN`
4. `V4__` -> `AUT`
5. Gap (`V5`, `V6`, `V7`) for future modules.
6. `V8__` -> `MGT`
7. `V9__` -> `CAT`
8. `V10__` -> `CLN`

### Package Naming Convention
All classes follow a strict hierarchical structure:
`com.amachi.app.[module_name].[feature].[layer]`
Example: `com.amachi.app.vitalia.clinical.patient.service.PatientService`

---

## 🚀 Getting Started

### Prerequisites
*   Java 21
*   Maven 3.9+
*   MySQL/MariaDB

### Build and Install
From the project root:
```bash
mvn clean install -DskipTests
```

### Running the Application
Run the main class in `vitalia-boot`:
```bash
mvn spring-boot:run -pl vitalia-boot
```

## 👥 Roles & Permisos

El sistema utiliza un control de acceso basado en roles (RBAC) diseñado para entornos multi-tenant, asegurando que cada usuario tenga el nivel de visibilidad y acción adecuado.

### 🥇 SUPER_ADMIN (Administrador Global)
Es el rol de mayor jerarquía en la plataforma. Sus responsabilidades incluyen:
*   **Gestión de Organizaciones:** Alta, baja y modificación de *Tenants* (Clínicas/Hospitales).
*   **Gobierno de Datos:** Único responsable de la gestión de catálogos maestros clínicos globales (MDM), asegurando la estandarización para todos los clientes.
*   **Seguridad Global:** Administración de otros usuarios `SUPER_ADMIN` y creación del primer `TENANT_ADMIN` para cada organización.
*   **Aislamiento:** Aunque tiene acceso técnico a todas las bases de datos, opera bajo una política de **Zero Trust**, requiriendo una impersonación explícita para visualizar datos sensibles de un cliente.

### 🥈 ADMIN (Administrador de Organización)
Es el administrador local asignado a una organización específica.
*   **Gestión Local:** Administra usuarios, profesionales de salud y configuraciones dentro de su propia clínica.
*   **Aislamiento Estricto:** No tiene visibilidad ni acceso a datos de otras organizaciones. Su alcance está limitado al identificador de su *Tenant*.

### 🩺 DOCTOR
Personal médico con capacidad operativa dentro de una clínica.
*   **Gestión Clínica:** Realización de diagnósticos (CIE-10), órdenes de procedimientos (CUPS) y prescripción de medicamentos.
*   **Historia Clínica:** Acceso y actualización de la información médica evolutiva del paciente.

### 👩‍⚕️ NURSE (Enfermería)
Personal asistencial de apoyo clínico.
*   **Atención Primaria:** Registro de constantes vitales, administración de medicamentos/vacunas y apoyo en procedimientos médicos.

### 👤 PATIENT (Paciente)
El eje central del ecosistema Vitalia.
*   **Auto-gestión:** (Capacidad futura) Consulta de historial clínico, resultados de laboratorio, esquemas de vacunación y gestión de citas.

### 💼 EMPLOYEE (Personal Administrativo)
Personal de apoyo operativo de la clínica.
*   **Admisiones:** Gestión de datos demográficos del paciente, verificación de aseguradoras y procesos administrativos de ingreso/egreso.

### ✅ Verification Guide (Manual Test)

Since automated tests are isolated in a specific profile, you can verify this behavior manually in your Development environment using HTTP Clients (Postman/Curl).

#### 1. Setup Data
Assume we have:
- **Tenant A (ID: 100)** -> Has Person "Juan"
- **Tenant B (ID: 200)** -> Has Person "Pedro"
- **SuperAdmin** -> Global Access (but strictly filtered by Context)

#### 2. Test Cases

**Case A: Isolation (Tenant A vs B)**
Authenticate as TenantAdmin for **Tenant A**. Call `GET /api/v1/persons`.
- **Expected:** Returns only "Juan".
- **Security Check:** If "Pedro" appears, **ISOLATION IS BROKEN**.

Authenticate as TenantAdmin for **Tenant B**. Call `GET /api/v1/persons`.
- **Expected:** Returns only "Pedro".
- **Security Check:** If "Juan" appears, **ISOLATION IS BROKEN**.

**Case B: Zero Trust (No Context)**
Authenticate as **SuperAdmin** (Global Login), assuming NO tenant is selected in header `X-Tenant-ID`.
Call `GET /api/v1/persons`.
- **Expected:** Returns `[]` (Empty List).
- **Why?** Even SuperAdmin is subject to Zero Trust. To see data, SuperAdmin MUST impersonate a tenant explicitly (e.g., via `X-Tenant-ID: 100`).

#### 3. Troubleshooting
If you suspect data leakage:
1. Check logs for `TenantFilterAspect`: `🛡️ Tenant Isolation ACTIVE for TenantID: 100`.
2. Ensure you are NOT using native queries (`nativeQuery = true`) in Repositories without manually adding the join, as Hibernate Filters do not apply to native SQL.
