-- ============================================================
-- Script: V5_23__create_med_observation.sql
-- Módulo: vitalia-medical
-- Descripción: Creación de la tabla MED_OBSERVATION (SaaS Elite Tier).
-- ============================================================

CREATE TABLE IF NOT EXISTS MED_OBSERVATION (
    -- ==========================================
    -- Identidad y Auditoría Base
    -- ==========================================
    ID                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    TENANT_ID           VARCHAR(50) NOT NULL,
    EXTERNAL_ID         VARCHAR(36) NOT NULL UNIQUE,
    VERSION             BIGINT DEFAULT 0 NOT NULL,
    IS_DELETED          BOOLEAN DEFAULT FALSE NOT NULL,

    -- ==========================================
    -- Relaciones
    -- ==========================================
    FK_ID_PATIENT       BIGINT NOT NULL,
    FK_ID_ENCOUNTER     BIGINT,                          -- Encuentro asociado
    FK_ID_DOCTOR        BIGINT NOT NULL,                 -- Quien tomó la muestra/medición

    -- ==========================================
    -- Datos de la Observación (FHIR)
    -- ==========================================
    STATUS              VARCHAR(30) NOT NULL,            -- PRELIMINARY, FINAL, AMENDED, CANCELLED
    OBS_CODE            VARCHAR(100) NOT NULL,           -- LOINC o código interno
    DISPLAY_NAME        VARCHAR(200),
    VALUE_TEXT          TEXT,                            -- Valor o resultado
    UNIT                VARCHAR(30),                     -- Unidad de medida (Ej: mmHg, mg/dL)
    REFERENCE_RANGE     VARCHAR(100),                    -- Rango de referencia
    INTERPRETATION      VARCHAR(50),                     -- Ej: NORMAL, HIGH, LOW
    EFFECTIVE_DATETIME  DATETIME(6) NOT NULL,            -- Fecha/hora de la medición

    NOTES               TEXT,

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
    CONSTRAINT FK_MED_OBS_PATIENT FOREIGN KEY (FK_ID_PATIENT) REFERENCES MED_PATIENT(ID),
    CONSTRAINT FK_MED_OBS_ENC     FOREIGN KEY (FK_ID_ENCOUNTER) REFERENCES MED_ENCOUNTER(ID),
    CONSTRAINT FK_MED_OBS_DOC     FOREIGN KEY (FK_ID_DOCTOR)  REFERENCES MED_DOCTOR(ID),

    INDEX IDX_OBS_TENANT  (TENANT_ID),
    INDEX IDX_OBS_PATIENT (FK_ID_PATIENT),
    INDEX IDX_OBS_CODE    (OBS_CODE),
    INDEX IDX_OBS_DATE    (EFFECTIVE_DATETIME)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
