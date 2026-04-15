# Elite Surgery Transition Plan: Composition vs Inheritance

## 1. Visión General
Este documento detalla la migración del modelo de **Herencia de Identidad** (`Patient extends Person`) al modelo de **Composición de Identidad Elástica** (`Patient has a Person`). Este cambio es fundamental para alcanzar el estándar **SaaS Elite Tier**, habilitando la interoperabilidad global y el aislamiento multi-tenant absoluto.

---

## 2. Nueva Estructura de Entidades (Java)

### A. Definición de Rol (Contexto Tenant)
Cada rol clínico (Patient, Doctor, Nurse, Employee) debe ser independiente y aislado:
*   **Extensión**: `extends BaseTenantEntity` (Aislamiento nativo).
*   **Composición**: Referencia a `Person` mediante una relación ManyToOne.
    ```java
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FK_ID_PERSON", nullable = false)
    private Person person;
    ```

### B. Unicidad e Integridad (SaaS Elite Hardened)
*   **Constraint DB**: Se debe garantizar que una persona tenga un solo registro por rol dentro de un mismo inquilino:
    `CONSTRAINT UNIQUE (FK_ID_PERSON, TENANT_ID, IS_DELETED)`
*   **Índices**: Todas las tablas de rol **DEBEN** tener un índice explícito en la columna de identidad para evitar degradación en FETCH JOINS:
    `INDEX IDX_[ROLE]_PERSON (FK_ID_PERSON)`
*   **PK Strategy**: Cada rol ahora tiene su propio `ID BIGINT AUTO_INCREMENT` independiente del ID de la persona.

### C. Gestión de Ciclo de Vida y Cascadas
*   **Cascadas Prohibidas**: Para proteger la integridad de la Identidad Global, **está prohibido** usar `CascadeType.ALL` o `REMOVE` hacia `Person`.
    `@ManyToOne(cascade = {}, fetch = FetchType.LAZY, optional = false)`
*   **Borrado Lógico**: El `SoftDelete` en el rol es local al tenant. `Person` es inmutable ante acciones locales de borrado.

### D. Gobernanza de Identidad (Identity Governance)
*   **Ancla Global**: El `nationalId` en `Person` es la clave de unicidad global.
*   **Validación de Servicio**: Antes de crear un rol, el servicio **DEBE** validar la existencia de `Person` y la no duplicidad del rol activo para ese `personId` y `tenantId`.

### E. Estrategia de Performance y Escalabilidad
*   **Consultas**: Usar `JOIN FETCH` quirúrgicamente en `Specifications`.
*   **Proyecciones**: Para listados masivos, usar `Interfaces de Proyección` para evitar el overhead de carga de entidades completas.

---

## 3. Estrategia de Mapeo y Puentes (Compatibilidad)
*   **Puentes Temporales**: Solo se permiten métodos puente de lectura (`getFirstName`, etc.) marcados con `@Deprecated` y `@Transient`. 
*   **Death Clock (Reloj de Muerte)**: Estos puentes deben ser eliminados en el **Sprint N+1**. Su única función es mantener la estabilidad durante la transición.
*   **Delegación en Mapper**: El Mapper es el único responsable de aplanar la jerarquía:
    `dto.firstName -> entity.getPerson().getFirstName()`

---

## 4. Mantra Elite: Identidad vs Contexto
1. **Identidad (Global)**: Quién es el ser humano (`Person`). Universal.
2. **Contexto (Tenant)**: Qué hace el ser humano en un hospital específico. Aislado.
3. **Regla de Oro**: Nunca mover datos de identidad al contexto de rol.

---
**Certificado por Antigravity - SaaS Elite Tier Architecture**
