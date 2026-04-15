-- ============================================================
-- Script: V9_23__create_cat_medical_unit_type.sql
-- Módulo: vitalia-medical-catalog
-- Descripción: Catálogo Global de Tipos de Unidades Médicas (SaaS Elite Tier)
-- ============================================================

CREATE TABLE IF NOT EXISTS CAT_MEDICAL_UNIT_TYPE (
    ID                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    CODE                VARCHAR(20) NOT NULL UNIQUE,
    NAME                VARCHAR(150) NOT NULL,
    DESCRIPTION         VARCHAR(500),
    ACTIVE              BOOLEAN DEFAULT TRUE NOT NULL,

    -- ==========================================
    -- Concurrencia e ID Externo (Elite Tier)
    -- ==========================================
    VERSION             BIGINT DEFAULT 0 NOT NULL,
    EXTERNAL_ID         VARCHAR(36) NOT NULL UNIQUE,

    -- ==========================================
    -- Auditoría Base (Global)
    -- ==========================================
    CREATED_BY          VARCHAR(100) NOT NULL,
    CREATED_DATE        DATETIME(6)  NOT NULL,
    LAST_MODIFIED_BY    VARCHAR(100),
    LAST_MODIFIED_DATE  DATETIME(6)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
