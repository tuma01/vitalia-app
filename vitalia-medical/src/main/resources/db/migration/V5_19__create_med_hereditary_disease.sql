-- Script: V5_19__create_med_hereditary_disease.sql
-- Módulo: vitalia-medical
-- Descripción: Creación de la tabla MED_HEREDITARY_DISEASE (SaaS Elite Tier).
-- Autor: Juan Amachi
-- ============================================================

CREATE TABLE IF NOT EXISTS MED_HEREDITARY_DISEASE (
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
    FK_ID_FAMILY_HISTORY BIGINT NOT NULL,
    FK_ID_KINSHIP       BIGINT,                          -- Vínculo al catálogo de parentesco

    -- ==========================================
    -- Datos Clínicos (English Standard)
    -- ==========================================
    DISEASE_NAME        VARCHAR(150) NOT NULL,
    REMARK              VARCHAR(500),
    DIAGNOSIS_DATE      DATE,

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
    CONSTRAINT FK_MED_ENFER_FAMILIAR FOREIGN KEY (FK_ID_FAMILY_HISTORY) REFERENCES MED_FAMILY_HISTORY(ID),
    
    INDEX IDX_HER_DIS_TENANT (TENANT_ID),
    INDEX IDX_HER_DIS_FAM    (FK_ID_FAMILY_HISTORY)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
