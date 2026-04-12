-- Script: V5_9__create_med_patient_visit.sql
-- Módulo: vitalia-medical
-- Descripción: Creación de la tabla MED_PATIENT_VISIT (SaaS Elite Tier).
-- Autor: Juan Amachi
-- ============================================================

CREATE TABLE IF NOT EXISTS MED_PATIENT_VISIT (
    -- ==========================================
    -- Identidad y Auditoría Base
    -- ==========================================
    ID                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    TENANT_ID           VARCHAR(50) NOT NULL,
    EXTERNAL_ID         VARCHAR(36) NOT NULL UNIQUE,
    VERSION             BIGINT DEFAULT 0 NOT NULL,
    IS_DELETED          BOOLEAN DEFAULT FALSE NOT NULL,

    -- ==========================================
    -- Relaciones Operativas
    -- ==========================================
    FK_ID_PATIENT       BIGINT NOT NULL,
    FK_ID_DOCTOR        BIGINT,
    FK_ID_MEDICAL_HISTORY BIGINT,

    -- ==========================================
    -- Datos de la Visita
    -- ==========================================
    VISIT_DATETIME      DATETIME(6) NOT NULL,
    VISIT_TYPE          VARCHAR(50) NOT NULL,            -- CONSULTA_EXTERNA, EMERGENCIAS, etc.
    VISIT_STATUS        VARCHAR(50) NOT NULL,            -- PLANNED, IN_PROGRESS, COMPLETED
    APPOINTMENT_REF     VARCHAR(100),
    DURATION_MINUTES    INT,
    TRIAGE_LEVEL        VARCHAR(20),
    NOTES               VARCHAR(1000),
    IS_FOLLOW_UP_REQUIRED BOOLEAN DEFAULT FALSE NOT NULL,
    FOLLOW_UP_DATE      DATE,

    -- ==========================================
    -- Auditoría de Operación
    -- ==========================================
    CREATED_BY          VARCHAR(100) NOT NULL,
    CREATED_DATE        DATETIME(6) NOT NULL,
    LAST_MODIFIED_BY    VARCHAR(100),
    LAST_MODIFIED_DATE  DATETIME(6),

    -- ==========================================
    -- Constraints & Indexes
    -- ==========================================
    CONSTRAINT FK_MED_VIS_PATIENT FOREIGN KEY (FK_ID_PATIENT) REFERENCES MED_PATIENT(ID),
    CONSTRAINT FK_MED_VIS_DOCTOR  FOREIGN KEY (FK_ID_DOCTOR)  REFERENCES MED_DOCTOR(ID),
    
    INDEX IDX_PV_TENANT  (TENANT_ID),
    INDEX IDX_PV_PATIENT (FK_ID_PATIENT),
    INDEX IDX_PV_DATE    (VISIT_DATETIME)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
