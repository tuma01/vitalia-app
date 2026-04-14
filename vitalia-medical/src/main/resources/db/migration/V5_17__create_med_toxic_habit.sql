-- ============================================================
-- Script: V5_17__create_med_toxic_habit.sql
-- Módulo: vitalia-medical
-- Descripción: Creación de la tabla MED_TOXIC_HABIT (SaaS Elite Tier).
-- ============================================================

CREATE TABLE IF NOT EXISTS MED_TOXIC_HABIT (
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
    FK_ID_MEDICAL_HISTORY BIGINT NOT NULL,

    -- ==========================================
    -- Datos Clínicos (English Standard)
    -- ==========================================
    IS_CURRENT          BOOLEAN DEFAULT TRUE NOT NULL,
    ALCOHOL             VARCHAR(50) NOT NULL,
    TOBACCO             VARCHAR(200),
    CIGARETTES_PER_DAY  INT,
    START_AGE           INT,
    QUIT_DATE           DATE,
    DRUGS               VARCHAR(500),
    CAFFEINE            VARCHAR(200),
    SEDENTARY_LIFESTYLE VARCHAR(200),
    OTHERS              VARCHAR(500),

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
    CONSTRAINT FK_MED_HABTOX_HISTORY FOREIGN KEY (FK_ID_MEDICAL_HISTORY) REFERENCES MED_MEDICAL_HISTORY(ID),
    
    INDEX IDX_TOX_HAB_TENANT  (TENANT_ID),
    INDEX IDX_TOX_HAB_HISTORY (FK_ID_MEDICAL_HISTORY),
    CONSTRAINT UK_TOXHAB_TENANT_HISTORY UNIQUE (TENANT_ID, FK_ID_MEDICAL_HISTORY, IS_CURRENT)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
