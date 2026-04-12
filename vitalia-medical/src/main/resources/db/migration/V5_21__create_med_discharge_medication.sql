-- Script: V5_21__create_med_discharge_medication.sql
-- Módulo: vitalia-medical
-- Descripción: Creación de la tabla MED_DISCHARGE_MEDICATION (SaaS Elite Tier).
-- Autor: Juan Amachi
-- ============================================================

CREATE TABLE IF NOT EXISTS MED_DISCHARGE_MEDICATION (
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
    FK_ID_HOSPITALIZATION BIGINT NOT NULL,              -- Registro de hospitalización vinculado
    FK_ID_MEDICATION      BIGINT,                       -- Catálogo global de medicamentos

    -- ==========================================
    -- Datos de la Receta al Alta
    -- ==========================================
    MEDICATION_NAME_DISPLAY VARCHAR(250),               -- Presentación específica (Ej: Paracetamol Complex)
    DOSAGE                VARCHAR(100),                 -- Ej: 500mg
    FREQUENCY             VARCHAR(100),                 -- Ej: Cada 8 horas
    DURATION              VARCHAR(100),                 -- Ej: 7 días
    INSTRUCTIONS          TEXT,                         -- Instrucciones adicionales

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
    CONSTRAINT FK_MED_DIS_HOSP FOREIGN KEY (FK_ID_HOSPITALIZATION) REFERENCES MED_HOSPITALIZATION(ID),
    
    INDEX IDX_DIS_MED_TENANT (TENANT_ID),
    INDEX IDX_DIS_MED_HOSP   (FK_ID_HOSPITALIZATION)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
