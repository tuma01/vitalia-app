-- ============================================================
-- Script: V5_25__create_med_past_illness.sql
-- Módulo: vitalia-medical
-- Descripción: Creación de la tabla MED_PAST_ILLNESS (SaaS Elite Tier).
-- ============================================================

CREATE TABLE IF NOT EXISTS MED_PAST_ILLNESS (
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
    FK_ID_HIST_MED      BIGINT NOT NULL,

    -- ==========================================
    -- Datos Clínicos
    -- ==========================================
    DISEASE_NAME        VARCHAR(150) NOT NULL,
    DESCRIPTION         VARCHAR(500),
    SYMPTOMS            VARCHAR(500),
    TREATMENTS          VARCHAR(500),

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
    CONSTRAINT FK_MED_PAST_ILL_HISTORY FOREIGN KEY (FK_ID_HIST_MED) REFERENCES MED_MEDICAL_HISTORY(ID),
    
    INDEX IDX_PAST_ILL_TENANT  (TENANT_ID),
    INDEX IDX_PAST_ILL_HISTORY (FK_ID_HIST_MED)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
