# Vitalia Medical Core - Arquitectura Técnica (Elite Tier)

Este documento detalla la arquitectura de grado empresarial e internacional del módulo **Medical Core**, diseñada bajo el estándar **HL7 FHIR** para sistemas de gestión hospitalaria (EHR/HIS).

---

## 🏗️ 1. Módulos y Capas de la Arquitectura

El sistema se divide en tres motores orquestadores que separan la logística del acto médico crítico:

### A. Scheduling Engine (Motor de Logística)
Gestiona la agenda, disponibilidad y recepción de pacientes.
- **Entidades**: `Appointment`, `AppointmentReminder`, `DoctorAvailability`.
- **Collision Engine**: Sistema de detección automática de solapamientos para Médicos, Pacientes e Infraestructura (Consultorios).
- **Concurrency**: Implementa **Bloqueo Pesimista (FOR UPDATE)** para evitar Double-Booking en entornos de alta concurrencia.

### B. Encounter Lifecycle (Corazón Clínico)
Gestiona el acto médico real desde el Check-In hasta el Cierre de la atención.
- **Entidad Central**: `Encounter` (FHIR Encounter).
- **Clinical Integrity**: Valida que no se cierre un encuentro sin un diagnóstico formal (`Condition`) vinculado al catálogo **CIE-10**.
- **Estados Clave**: `PLANNED`, `IN_PROGRESS`, `ON_HOLD`, `COMPLETED`, `CANCELLED`.

### C. Clinical Search Engine (Motor Analítico)
Motor de búsqueda transversal para minería de datos clínicos.
- **Capabilities**: Búsqueda avanzada por códigos **LOINC** (Observaciones), **CIE-10** (Diagnósticos) y rangos de zonas horarias globales.
- **Clinical Timeline**: Genera el `ClinicalHistoryStream`, una vista cronológica unificada de todos los actos médicos de un paciente.

---

## 🛡️ 2. Estándares de Datos y Calidad

### Global SaaS Tier (Timezones)
- **OffsetDateTime**: Todas las marcas de tiempo críticas utilizan el estándar ISO-8601 con Offset para garantizar interoperabilidad legal y técnica en múltiples regiones (Canadá/SaaS Global).

### Identidad Clave (SSOT)
- **Tenant Isolation**: Cada registro está vinculado a un `TenantId`, garantizando el aislamiento total de datos entre diferentes instituciones de salud.
- **Practitioner Tracing**: Cada diagnóstico, medición u orden médica guarda el vínculo con el médico responsable de forma atómica.

---

## 🧬 3. Flujo Operativo Estándar (EHR Flow)

1.  **Agendamiento**: Se crea un `Appointment` asignando médico, paciente y consultorio. El `CollisionEngine` valida la disponibilidad.
2.  **Check-In**: El paciente llega. Se marca `checkedInAt`. Esto dispara el `AppointmentEvent`.
3.  **Inicio de Acto**: Se genera el `Encounter` (`IN_PROGRESS`). Se vincula a la cita y al expediente maestro (`MedicalHistory`).
4.  **Registro Clínico**: Durante el encuentro se añaden:
    - `Condition` (Diagnósticos CIE-10).
    - `Observation` (Signos vitales/Labs LOINC).
    - `MedicationRequest` (Receta electrónica).
5.  **Cierre**: El médico finaliza el acto. El sistema valida la integridad clínica y marca el `Appointment` como `COMPLETED` automáticamente.

---

## 🔍 4. Extensiones y Analítica

- **Clinical Specifications**: Uso de JPA Criteria dinámicos para búsquedas epidemiológicas (ej: "Pacientes con HTA en el hospital X").
- **Unified Stream**: Contrato `ClinicalEventDto` para visualización 360° en el Dashboard del Médico.

---

*Desarrollado por el equipo de Arquitectura Principal - Vitalia Platform.*
