-- ============================================================
-- Script: V5_9__create_med_encounter.sql
-- Módulo: vitalia-medical
-- Descripción: Creación de la tabla MED_ENCOUNTER (SaaS Elite Tier).
--             Sustituye a MED_PATIENT_VISIT para alineación FHIR.
-- ============================================================

CREATE TABLE IF NOT EXISTS MED_ENCOUNTER (
    -- ==========================================
    -- Identidad y Auditoría Base
    -- ==========================================
    ID                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    TENANT_ID           VARCHAR(50) NOT NULL,
    EXTERNAL_ID         VARCHAR(36) NOT NULL UNIQUE,
    VERSION             BIGINT DEFAULT 0 NOT NULL,
    IS_DELETED          BOOLEAN DEFAULT FALSE NOT NULL,

    -- ==========================================
    -- Datos del Encuentro Clínico
    -- ==========================================
    ENCOUNTER_DATE      DATETIME(6) NOT NULL,            -- Precisión para OffsetDateTime
    ENCOUNTER_TYPE      VARCHAR(30) NOT NULL,            -- AMBULATORY, EMERGENCY, etc.
    STATUS              VARCHAR(30) NOT NULL,            -- PLANNED, IN_PROGRESS, COMPLETED
    DURATION_MINUTES    INT,
    
    -- ==========================================
    -- Contenido Clínico
    -- ==========================================
    CHIEF_COMPLAINT     VARCHAR(500),                    -- Motivo de consulta
    CLINICAL_NOTES      TEXT,                            -- Notas detalladas
    TRIAGE_LEVEL        VARCHAR(30),                     -- Nivel de prioridad

    -- ==========================================
    -- Relaciones (FKs)
    -- ==========================================
    FK_ID_PATIENT       BIGINT NOT NULL,
    FK_ID_DOCTOR        BIGINT,
    FK_ID_HISTORY       BIGINT,                          -- Expediente vinculado
    FK_ID_APPT          BIGINT,                          -- Cita vinculada
    FK_ID_EPI           BIGINT,                          -- Episodio de cuidado

    -- ==========================================
    -- Auditoría de Operación
    -- ==========================================
    CREATED_BY          VARCHAR(100) NOT NULL,
    CREATED_DATE        DATETIME(6)  NOT NULL,
    LAST_MODIFIED_BY    VARCHAR(100),
    LAST_MODIFIED_DATE  DATETIME(6),

    -- ==========================================
    -- Constraints & Indexes
    -- ==========================================
    CONSTRAINT FK_MED_ENC_PATIENT FOREIGN KEY (FK_ID_PATIENT) REFERENCES MED_PATIENT(ID),
    CONSTRAINT FK_MED_ENC_DOCTOR  FOREIGN KEY (FK_ID_DOCTOR)  REFERENCES MED_DOCTOR(ID),
    
    INDEX IDX_ENC_TENANT  (TENANT_ID),
    INDEX IDX_ENC_STATUS  (STATUS),
    INDEX IDX_ENC_PATIENT (FK_ID_PATIENT)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
