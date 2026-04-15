# 🏗️ SaaS Elite Tier Elevation: Global Audit & Strategic Refactoring Plan

This document provides a comprehensive analysis of the current state of **amachi-platform** and outlines the global strategy for elevating all modules to the **SaaS Elite Tier** architectural standard.

## 1. 📊 Global Current State Analysis

| Module | Architectural Level | SaaS Elite Adherence | Key Observation |
|:---|:---:|:---:|:---|
| **core-common** | **Elite** | 100% | The kernel is ready. BaseEntity/Service/Spec follow Tier-1 standards. |
| **core-auth** | **Elite** | 100% | Identity management is decoupled and event-driven. |
| **core-management** | **Elite** | 100% | Tenant and Theme management updated to standards. |
| **core-geography** | **Elite** | 95% | Solid structure. Minor naming alignment in State/Municipality. |
| **core-domain** | **Elite** | 100% | **IDENTITY LAYER REFACTORED**. Person (Global) and Hospital (Tenant) standardized. |
| **vitalia-medical-catalog**| **High Gold** | 80% | Medication/Allergy updated. Other catalogs need BaseService migration. |
| **vitalia-medical** | **Gold** | 60% | Functional but monolithic. Needs global refactoring and tenant isolation. |
| **vitalia-boot** | **Solid** | 90% | Good orchestration. Needs final global filter registration. |

---

## 2. 🎯 The "SaaS Elite" Target Standard

To achieve pure Elite status, every module must strictly comply with its scope:

### A. Global Entities (Common Catalogs & Root Identity)
*   **Scope**: Data shared across all tenants (e.g., Countries, Provinces, Medical Standards).
*   **Core Entity**: **`Person`** (Shared Identity Pattern). Personal biological data belongs to the human, not the hospital.
*   **Hierarchy**: Must extend **`Auditable`** (which provides ID, Audit, Version and ExternalId).
*   **Isolation**: No isolation. These entities **DO NOT** contain `TENANT_ID`.
*   **Mapper Config**: Use **`@AuditableIgnoreConfig.IgnoreAuditableFields`** (Ignores Audit, Version, ExternalId).

### B. Tenant-Scoped Entities (SaaS Elevation)
*   **Scope**: Data private and isolated by organization (e.g., Patients, Medical Visits, Appointments).
*   **Hierarchy**: Must extend **`BaseTenantEntity`** (which extends `Auditable` and adds `TENANT_ID`).
*   **Systemic Multi-Tenancy**: Zero manual `tenantId` handling. Isolated automatically via Hibernate Filters.
*   **Mapper Config**: Use **`@AuditableIgnoreConfig.IgnoreTenantAuditableFields`** (Ignores Audit, Version, ExternalId and **TenantId**).

> [!IMPORTANT]
> **Caso especial — Entidad `Tenant` (tabla `DMN_TENANT`)**
> La entidad `Tenant` también extiende `BaseTenantEntity`, pero su campo `TENANT_ID` siempre vale `"SYSTEM"`.
> Esto es **intencional y correcto**: la tabla `DMN_TENANT` es el registro central de la plataforma Vitalia/Amachi,
> no de un hospital específico. Su propietario es `SYSTEM` — el nivel raíz de la plataforma.
> Todos los hospitales registrados en `DMN_TENANT` tendrán `TENANT_ID = "SYSTEM"`.
> Esto garantiza que **solo el SuperAdmin** (quien hace bypass del filtro Hibernate) pueda ver y gestionar todos los tenants.
> Los usuarios normales no pueden ver la tabla `DMN_TENANT` porque su filtro activo (`TENANT_ID = 'hospital-san-borja'`) no coincide con `SYSTEM`.

### C. Estándar de Configuración de Mappers (MapStruct)

Para evitar errores de compilación (`Unknown Property`) y asegurar el aislamiento multi-tenant, se debe seguir estrictamente este estándar en `AuditableIgnoreConfig.java`:

```java
@MapperConfig
public interface AuditableIgnoreConfig {
    // 🌍 Entidades GLOBALES: NO contienen tenantId
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "externalId", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @interface IgnoreAuditableFields {}

    // 🏢 Entidades de INQUILINO: SÍ contienen tenantId y debe ser ignorado
    @Mapping(target = "tenantId", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "externalId", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @interface IgnoreTenantAuditableFields {}
}
```

### D. Modelos de Referencia Obligatorios

> [!IMPORTANT]
> Antes de implementar cualquier nueva entidad, el asistente de IA y el desarrollador **DEBEN** consultar el template correspondiente y replicar su patrón al 100%.

#### 🌍 Template Global — Entidad `Country` (`core-geography`)

Para catálogos compartidos sin aislamiento por tenant:

| Componente | Archivo de referencia |
|---|---|
| Entidad | `Country.java` → extiende `Auditable<String>` |
| DTO | `CountryDto.java` → sin `tenantId`, sin campos de auditoría |
| Mapper | `CountryMapper.java` → usa `@IgnoreAuditableFields` |
| DDL | `V2_1__create_geo_country.sql` → sin columna `TENANT_ID` |

#### 🏥 Template Tenant-Scoped — Entidad `Appointment` (`vitalia-medical`) ✅ VALIDADO

Para datos de negocio aislados por organización. **Este template ha sido completamente validado** y debe usarse como referencia exacta para todas las entidades del módulo `vitalia-medical`:

| Componente | Archivo de referencia | Estado |
|---|---|:---:|
| Entidad | `Appointment.java` → extiende `BaseTenantEntity`, implementa `SoftDeletable` | ✅ |
| DTO | `AppointmentDto.java` → FKs como Long, campos READ_ONLY con `Schema.AccessMode` | ✅ |
| Mapper | `AppointmentMapper.java` → `@IgnoreTenantAuditableFields`, locks protegidos | ✅ |
| Service | `AppointmentServiceImpl.java` → extiende `BaseService`, event-driven | ✅ |
| Specification | `AppointmentSpecification.java` → extiende `BaseSpecification` | ✅ |
| API | `AppointmentApi.java` → `@NonNull`, `@Valid`, paginación correcta | ✅ |
| SearchDto | `AppointmentSearchDto.java` → sin `tenantId`, filtros de negocio only | ✅ |
| DDL CREATE | `V5_11__create_med_appointment.sql` → `DATETIME(6)`, 5 índices, FK completas | ✅ |
| DDL Relación | `V5_12__create_med_reminder.sql` → tabla hija con todos sus campos | ✅ |

### 🚨 Protocolo Crítico: Análisis de Identidad (MANDATORIO)
**ANTES de crear o modificar cualquier entidad**, el asistente de IA **DEBE PREGUNTAR** al usuario si el recurso pertenece al alcance **Global** o **SaaS Elite**.
*   **PROHIBICIÓN**: El asistente NO tiene autorización para decidir el alcance por cuenta propia.
*   **ANÁLISIS**: Se debe realizar un análisis conjunto del impacto antes de tocar el código.

### ⛔ Módulos Prohibidos para Aislamiento (REGLA DE ORO)
Está **TERMINANTEMENTE PROHIBIDO** elevar a nivel SaaS Elite (aislamiento por `TENANT_ID`) los siguientes módulos, ya que son **CATÁLOGOS GLOBALES** estándar:
1.  **`core-geography`**: Países, Provinces, etc.
2.  **`vitalia-medical-catalog`**: Alergias, CIE-10, Medicamentos, Vacunas, Procedimientos, Especialidades, Parentescos, Tipos de Identificación, Géneros y Estados Civiles.
*   **Razón**: Estos módulos representan conocimiento médico y demográfico universal. Su fragmentación por Tenant es un error arquitectónico grave, crea redundancia masiva y dificulta el intercambio de datos (Interoperabilidad).

### D. Common Requirements (All Entities)
1.  **Event-Driven Consistency**: Every mutating operation (Create/Update/Delete) must publish a `DomainEvent`.
2.  **Premium Resilience**: Optimistic Locking (`@Version`) and globally unique `EXTERNAL_ID` (UUID).
3.  **Professional Standardization**: 100% English naming for all JPA entities, services, and APIs.
4.  **Backend Naming Convention**:
    *   **Packages**: Always **singular** (e.g., `com.amachi.app.core.geography.country`).
    *   **Entities**: Always **singular** (e.g., `Country.java`).
    *   **API/Endpoints**: Always **plural** (e.g., `/api/v1/countries`).
    *   **Controllers/Services**: Follow the entity name (singular) or purpose (e.g., `CountryController`).

### E. 🔑 Patrón Platform-as-a-Tenant (Referencia Definitiva)

Esta sección documenta cómo funciona el aislamiento multi-tenant de extremo a extremo en Amachi Platform.

#### Los 3 identificadores del Tenant y su rol exacto

| Campo | Tipo | Ubicación | Para qué sirve |
|---|---|---|---|
| `id` | `Long` | `BaseEntity` | PK interna de BD. **Nunca** se expone al exterior ni se usa para filtrar. |
| `code` | `String` | `Tenant.code` | **El identificador real del tenant** en todo el sistema. Se usa en JWT, headers HTTP, filtro Hibernate y bootstrap. Ej: `"hospital-san-borja"`. |
| `tenantId` | `String` | `BaseTenantEntity` | Valor almacenado en la columna `TENANT_ID` de cada tabla de datos. Su valor **ES el `code`** del tenant dueño del dato. |

> [!CAUTION]
> **NUNCA** usar el `id` (Long) del Tenant para filtrar datos. El sistema opera 100% con el `code` (String).
> El campo `TENANT_ID` en las tablas como `MED_APPOINTMENT` almacena el valor `"hospital-san-borja"`, NO el número `1`.

#### Flujo completo de una petición HTTP (de extremo a extremo)

```
1. Bootstrap carga → code: "hospital-san-borja"  (desde application.yml)
              ↓
2. Frontend envía → Header X-Tenant-Code: "hospital-san-borja"
              ↓
3. MultiTenantFilter → TenantResolver.resolveTenant()  captura "hospital-san-borja"
              ↓
4. TenantContext.setTenant("hospital-san-borja")  (ThreadLocal, seguro por hilo)
              ↓
5. TenantFilterAspect (AOP) activa filtro Hibernate:
   session.enableFilter("tenantFilter").setParameter("tenantId", "hospital-san-borja")
              ↓
6. Hibernate añade WHERE TENANT_ID = 'hospital-san-borja' a CADA query automáticamente
              ↓
7. BaseTenantEntity.@PrePersist → si tenantId == null → setTenantId(TenantContext.getTenant())
   (el TENANT_ID del nuevo registro se inyecta automáticamente al crear)
              ↓
8. JWT contiene → claim tenantCode: "hospital-san-borja"  (via JwtUserDto.tenantCode)
```

#### Regla de Oro del DTO para entidades Tenant-Scoped

Los DTOs de entidades que extienden `BaseTenantEntity` **NUNCA** deben incluir `tenantId`.
El campo `TENANT_ID` es gestionado automáticamente por la infraestructura en ambas direcciones:
- **Escritura**: `@PrePersist` en `BaseTenantEntity` lo inyecta desde `TenantContext`.
- **Lectura**: `@IgnoreTenantAuditableFields` en el Mapper lo ignora explícitamente.
- **Filtrado**: `TenantFilterAspect` lo aplica automáticamente en cada query.

#### Modelo de Referencia: `AppointmentDto` (Template Oficial)

El DTO de referencia para entidades Tenant-Scoped es `AppointmentDto`. Todo nuevo DTO debe seguir este patrón:
- ✅ IDs de FK como `Long` (ej: `patientId`, `doctorId`) — nunca el objeto JPA completo.
- ✅ Campos de solo lectura computados (ej: `patientFullName`, `doctorFullName`, `unitName`).
- ✅ Campos operacionales que el cliente puede enviar (ej: `source`, `noShow`, `status`).
- ✅ Campos de infraestructura read-only con `Schema.AccessMode.READ_ONLY` (ej: `lockedUntil`, `lockedBy`).
- ❌ Sin `tenantId` — gestionado por infraestructura.
- ❌ Sin `version` ni `externalId` en el DTO — gestionados por JPA/Hibernate automáticamente.
- ❌ Sin `createdBy`, `createdDate`, `lastModifiedBy`, `lastModifiedDate` — campos de auditoría internos.

---

## 3. 🛠️ Execution Strategy: Global Refactoring

### Phase 1: Database Schema Alignment (Flyway)
**Objective**: Standardize all medical tables to include the Elite Tier required audit and isolation columns.
*   **New Migration**: `V5_20__upgrade_medical_schema_elite.sql`
*   **Actions**:
    *   Add `TENANT_ID` (VARCHAR 50) to all tables lacking it (e.g., `MED_APPOINTMENT`).
    *   Add `EXTERNAL_ID` (VARCHAR 36) and `VERSION` (BIGINT) to support Idempotency and Optimistic Locking.
    *   Add `deleted` (TINYINT) to support standardized Soft-Delete.

### Phase 2: Entity & Service Refactoring (JPA Level)
**Objective**: Rename Spanish entities to English and migrate logic to `BaseService`.
*   **Naming Map**:
    *   `ConsultaMedica` ➡️ `Consultation`
    *   `TipoConsulta` ➡️ `ConsultationType`
    *   `Hospitalizacion` ➡️ `Hospitalization`
    *   `HabitoFisiologico` ➡️ `PhysiologicalHabit`
    *   ... (and all related fields)
*   **Infrastructure**:
    *   All entities extend `BaseEntity` or `SoftDeletableEntity`.
    *   All services extend `BaseService`.
    *   All specifications extend `BaseSpecification`.

### Phase 3: Integration & Cross-Module Messaging
**Objective**: Register global handlers and publish events.
*   Update `VitaliaApplication` with `@EnableJpaAuditing`.
*   Implement `DomainEventPublisher` hooks in `vitalia-medical`.

---

## 5. 🛡️ Advanced Enterprise Identity: The Hardened Elastic Pattern

Vitalia implementa un modelo de identidad de 3 capas (Persona -> Tenant -> Rol) con un endurecimiento estricto diseñado para redes hospitalarias enterprise.

### A. Capas de Identidad
1. **Layer 1: Universal Identity (`Person`)**: Identidad global sin `TENANT_ID`. Nombre, NationalID y Bio-data pertenecen al humano.
2. **Layer 2: Secure Membership (`PersonTenant`)**: Vincula a una `Person` con un `Tenant`. Controla el acceso y estado operativo.
3. **Layer 3: Operative Roles (`Patient`, `Doctor`, `Nurse`, `Employee`)**: Entidades de dominio aisladas por `TENANT_ID`. **Contrato Obligatorio**: Deben implementar la interfaz `DomainRole` (`getPerson()`, `getTenantId()`).

### B. Desacoplamiento UX vs Persistencia (Hardened Rule)
Para evitar el acoplamiento UI ↔ Dominio, el sistema impone una separación total:
- **`RoleContext` (UX/Security)**: Define la perspectiva y navegación del usuario (ej. "Entro como DOCTOR").
- **`DomainContext` (Backend/Persistencia)**: Define la entidad física que se gestiona en la BD.
- **Gobernanza**: El mapeo debe ser **centralizado** (ej. `ContextMapping`). Si no hay mapeo válido (ej. GUEST), no se crea entidad de dominio.

### C. Protocolo de Resolución de Identidad
1. **Búsqueda Estricta**: 1. `nationalId` ➔ 2. `email` ➔ 3. Creación. Esto evita la fragmentación de la identidad global.
2. **Aceptación Atómica**: El proceso de `acceptInvitation` debe resolver la identidad y crear el contexto en una única unidad de trabajo atómica.

### D. Seguridad de Integridad (Collision Handling)
Dado que MySQL no admite índices parciales nativos para estados activos, el sistema implementa la validación **`existsActive`**:
- **Regla**: Antes de crear un contexto (`DomainContext`), el servicio `PersonContextService` **DEBE** verificar si la persona ya posee ese rol activo en el tenant.
- **Impacto**: Previene la duplicación de registros críticos (ej. dos registros de Doctor para la misma persona) y garantiza la integridad del EHR.

### E. Aislamiento de Gobernanza
Los roles de **SuperAdmin** y **TenantAdmin** operan exclusivamente a nivel de plataforma y seguridad, sin contaminar el modelo clínico o operativo.

---

## 6. 🛡️ Resilience & Soft-Delete Protocol

All Elite Tier entities MUST provide data safety through logical deletion.

- **Interface**: `SoftDeletable`.
- **Implementation**:
    - Field: `isDeleted` (mapped to `IS_DELETED` TINYINT in SQL).
    - Method: `delete()` sets `isDeleted = true`.
- **Constraint Handling**: Unique constraints must ideally include the `IS_DELETED` column to allow re-creation of previously deleted business keys.

---

---

## 4. ⚠️ Risk Management: DDL Regressions
To fulfill the user's requirement of **"Zero regressions in DDL"**:
1.  **Table Names Stable**: JPA entities will be renamed (e.g., `@Entity(name="Consultation")`), but `@Table(name="MED_PATIENT_VISIT")` will remain consistent with existing DB structure.
2.  **Additive Migrations**: We will ONLY use additive SQL (ALTER TABLE) in Flyway version `V5_20` or higher. We will NOT modify existing `V1-V9` files.
3.  **Mapping Consistency**: We will use `@Column(name="NOMBRE_FIELD")` to map English properties to existing Spanish columns where necessary, maintaining database compatibility while achieving code purity.

---

## 📅 Next Step: DDL Infrastructure Upgrade (Core-Domain)
Synchronize the physical schema with the refactored entities.
1. Add columns for Soft-Delete and Audit consistency to `DMN_THEME` and `DMN_HOSPITAL`.
2. Transition `DMN_PERSON` to its Global (Shared) status.

