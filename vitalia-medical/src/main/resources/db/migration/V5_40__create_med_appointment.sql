-- ============================================================
-- Script: V5_11__create_med_appointment.sql
-- Módulo: vitalia-medical
-- Descripción: Creación de la tabla MED_APPOINTMENT (SaaS Elite Tier).
-- ============================================================

CREATE TABLE IF NOT EXISTS MED_APPOINTMENT (
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
    FK_ID_DOCTOR        BIGINT NOT NULL,
    FK_ID_UNIT          BIGINT,
    FK_ID_ROOM          BIGINT,
    FK_ID_ENCOUNTER     BIGINT,

    -- ==========================================
    -- Agenda y Tiempos
    -- ==========================================
    START_TIME          DATETIME(6) NOT NULL,
    END_TIME            DATETIME(6) NOT NULL,
    STATUS              VARCHAR(30) NOT NULL,            -- PLANNED, CHECKED_IN, COMPLETED, CANCELLED
    PRIORITY            VARCHAR(30),
    SOURCE              VARCHAR(30),
    REASON              VARCHAR(500),
    NO_SHOW             BOOLEAN DEFAULT FALSE NOT NULL,

    -- ==========================================
    -- Lifecycle
    -- ==========================================
    CHECKED_IN_AT       DATETIME(6),
    COMPLETED_AT        DATETIME(6),
    CANCELLED_AT        DATETIME(6),
    CANCEL_REASON       VARCHAR(500),

    -- ==========================================
    -- Control de Concurrencia
    -- ==========================================
    LOCKED_UNTIL        DATETIME(6),
    LOCKED_BY           VARCHAR(100),

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
    CONSTRAINT FK_MED_APP_PATIENT   FOREIGN KEY (FK_ID_PATIENT)   REFERENCES MED_PATIENT(ID),
    CONSTRAINT FK_MED_APP_DOCTOR    FOREIGN KEY (FK_ID_DOCTOR)    REFERENCES MED_DOCTOR(ID),
    CONSTRAINT FK_MED_APP_ENCOUNTER FOREIGN KEY (FK_ID_ENCOUNTER) REFERENCES MED_ENCOUNTER(ID),
    
    INDEX IDX_APP_TENANT  (TENANT_ID),
    INDEX IDX_APP_PATIENT (FK_ID_PATIENT),
    INDEX IDX_APP_DOCTOR  (FK_ID_DOCTOR),
    INDEX IDX_APP_TIME    (START_TIME, END_TIME)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
