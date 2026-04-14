-- ============================================================
-- Script: V5_15__create_med_family_history.sql
-- Módulo: vitalia-medical
-- Descripción: Creación de la tabla MED_FAMILY_HISTORY (SaaS Elite Tier).
-- ============================================================

CREATE TABLE IF NOT EXISTS MED_FAMILY_HISTORY (
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
    MOTHER_HEALTH_INFO  VARCHAR(300),
    FATHER_HEALTH_INFO  VARCHAR(300),
    CARDIAC_FAMHISTORY  VARCHAR(300),
    MENTAL_FAMHISTORY   VARCHAR(300),
    DIABETES_FAMHISTORY VARCHAR(300),
    FAMILY_ETHNICITY    VARCHAR(100),
    GENETIC_RISK_INDEX  VARCHAR(30),
    ADDITIONAL_NOTES    TEXT,

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
    CONSTRAINT FK_MED_FAMHIST_HISTORY FOREIGN KEY (FK_ID_MEDICAL_HISTORY) REFERENCES MED_MEDICAL_HISTORY(ID),
    
    INDEX IDX_FAM_HIST_TENANT  (TENANT_ID),
    INDEX IDX_FAM_HIST_HISTORY (FK_ID_MEDICAL_HISTORY),
    CONSTRAINT UK_FAMHIST_TENANT_HISTORY UNIQUE (TENANT_ID, FK_ID_MEDICAL_HISTORY, IS_CURRENT)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
