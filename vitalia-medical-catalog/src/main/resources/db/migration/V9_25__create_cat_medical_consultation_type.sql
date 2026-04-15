-- ============================================================
-- Script: V9_25__create_cat_medical_consultation_type.sql
-- Módulo: vitalia-medical-catalog
-- Descripción: Catálogo Global de Tipos de Consulta Médica (SaaS Elite Tier)
-- ============================================================

CREATE TABLE IF NOT EXISTS CAT_MEDICAL_CONSULTATION_TYPE (
    ID                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    CODE                VARCHAR(20) NOT NULL UNIQUE,
    NAME                VARCHAR(150) NOT NULL,
    DESCRIPTION         VARCHAR(500),
    FK_ID_SPECIALTY     BIGINT,                          -- Especialidad vinculada (Opcional)
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
    LAST_MODIFIED_DATE  DATETIME(6),

    CONSTRAINT FK_CAT_CONS_TYPE_SPEC FOREIGN KEY (FK_ID_SPECIALTY) REFERENCES CAT_MEDICAL_SPECIALTY(ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
