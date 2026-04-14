-- ============================================================
-- Script: V5_14__create_med_history.sql
-- Módulo: vitalia-medical
-- Descripción: Creación de la tabla MED_MEDICAL_HISTORY (SaaS Elite Tier).
-- ============================================================

CREATE TABLE IF NOT EXISTS MED_MEDICAL_HISTORY (
    -- ==========================================
    -- Identidad y Auditoría Base
    -- ==========================================
    ID                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    TENANT_ID           VARCHAR(50) NOT NULL,
    EXTERNAL_ID         VARCHAR(36) NOT NULL UNIQUE,
    VERSION             BIGINT DEFAULT 0 NOT NULL,
    IS_DELETED          BOOLEAN DEFAULT FALSE NOT NULL,

    -- ==========================================
    -- Datos de Identidad del Expediente
    -- ==========================================
    HISTORY_NUMBER      VARCHAR(50) NOT NULL,            -- Código funcional (HC-2026-X)
    DOCUMENT_UUID       VARCHAR(36),                     -- Referencia UUID global
    FK_ID_PATIENT       BIGINT NOT NULL,                 -- Vínculo directo con el paciente
    FK_ID_PERSON        BIGINT,                          -- Datos demográficos (DMN_PERSON)
    FK_ID_DR_RESP       BIGINT,                          -- Médico asignado/responsable (MED_DOCTOR)

    -- ==========================================
    -- Control Operativo y Clínico
    -- ==========================================
    RECORD_DATE         DATE NOT NULL,                   -- Fecha de apertura
    VALID_UNTIL         DATE,                            -- Fecha de vigencia (opcional)
    IS_CURRENT          BOOLEAN DEFAULT TRUE NOT NULL,
    RECORD_VERSION      INT DEFAULT 1 NOT NULL,
    STATUS              VARCHAR(30) DEFAULT 'ACTIVE',    -- ACTIVE, ARCHIVED, LOCKED
    CONFIDENTIALITY_LEVEL VARCHAR(30) DEFAULT 'NORMAL',  -- NORMAL, VIP, SECRET
    IS_LOCKED           BOOLEAN DEFAULT FALSE,
    IS_ORGAN_DONOR      BOOLEAN DEFAULT FALSE,

    -- ==========================================
    -- Notas y Observaciones
    -- ==========================================
    OBSERVATIONS        TEXT,                            -- Observaciones clínicas largas
    NOTES               TEXT,                            -- Notas administrativas

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
    CONSTRAINT FK_MED_HIST_PATIENT FOREIGN KEY (FK_ID_PATIENT) REFERENCES MED_PATIENT(ID),
    CONSTRAINT FK_MED_HIST_PERSON  FOREIGN KEY (FK_ID_PERSON)  REFERENCES DMN_PERSON(ID),
    CONSTRAINT FK_MED_HIST_DR      FOREIGN KEY (FK_ID_DR_RESP) REFERENCES MED_DOCTOR(ID),

    INDEX IDX_HIST_TENANT  (TENANT_ID),
    INDEX IDX_HIST_PATIENT (FK_ID_PATIENT),
    INDEX IDX_HIST_NUMBER  (HISTORY_NUMBER),
    CONSTRAINT UK_HIST_TENANT_NUMBER UNIQUE (TENANT_ID, HISTORY_NUMBER)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
