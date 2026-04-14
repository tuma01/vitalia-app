-- ============================================================
-- Script: V5_27__create_med_medication_request.sql
-- Módulo: vitalia-medical
-- Descripción: Creación de la tabla MED_MEDICATION_REQUEST (SaaS Elite Tier).
-- ============================================================

CREATE TABLE IF NOT EXISTS MED_MEDICATION_REQUEST (
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
    FK_ID_ENCOUNTER     BIGINT,
    FK_ID_DOCTOR        BIGINT NOT NULL,
    FK_ID_MEDICATION    BIGINT,

    -- ==========================================
    -- Datos de la Receta (FHIR)
    -- ==========================================
    MEDICATION_DISPLAY_NAME VARCHAR(250),
    STATUS              VARCHAR(30) NOT NULL,            -- ACTIVE, ON-HOLD, CANCELLED, COMPLETED
    AUTHORED_ON         DATETIME(6) NOT NULL,
    DOSAGE_INSTRUCTION  TEXT,
    ROUTE               VARCHAR(50),                     -- ORAL, IV, etc.
    FREQUENCY           VARCHAR(50),                     -- 8H, 12H, etc.
    QUANTITY            DECIMAL(10,2),
    PRIORITY            VARCHAR(20),                     -- ROUTINE, URGENT, STAT
    REASON_CODE         VARCHAR(100),
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
    CONSTRAINT FK_MED_REQ_PATIENT FOREIGN KEY (FK_ID_PATIENT) REFERENCES MED_PATIENT(ID),
    CONSTRAINT FK_MED_REQ_ENC     FOREIGN KEY (FK_ID_ENCOUNTER) REFERENCES MED_ENCOUNTER(ID),
    CONSTRAINT FK_MED_REQ_DOC     FOREIGN KEY (FK_ID_DOCTOR) REFERENCES MED_DOCTOR(ID),
    CONSTRAINT FK_MED_REQ_MED     FOREIGN KEY (FK_ID_MEDICATION) REFERENCES CAT_MEDICATION(ID),
    
    INDEX IDX_MED_REQ_TENANT  (TENANT_ID),
    INDEX IDX_MED_REQ_PATIENT (FK_ID_PATIENT),
    INDEX IDX_MED_REQ_DATE    (AUTHORED_ON)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
