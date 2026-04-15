-- Script: V9_9__create_cat_medical_specialty.sql
-- Módulo: vitalia-medical-catalog
-- Descripción: Creación de la tabla CAT_MEDICAL_SPECIALTY (POOL GLOBAL PURO).
-- ============================================================

CREATE TABLE IF NOT EXISTS CAT_MEDICAL_SPECIALTY (
    ID              BIGINT AUTO_INCREMENT PRIMARY KEY,
    CODE            VARCHAR(20)  NOT NULL UNIQUE,
    NAME            VARCHAR(150) NOT NULL,
    DESCRIPTION     VARCHAR(500),
    TARGET_PROFESSION VARCHAR(20) DEFAULT 'BOTH',
    ACTIVE          BOOLEAN      DEFAULT TRUE,

    -- Concurrencia e ID Externo (Tier Elite)
    VERSION BIGINT NOT NULL DEFAULT 0,
    EXTERNAL_ID VARCHAR(36)  NOT NULL UNIQUE,

    -- Auditoría
    CREATED_BY VARCHAR(100) NOT NULL,
    CREATED_DATE DATETIME(6) NOT NULL,
    LAST_MODIFIED_BY VARCHAR(100),
    LAST_MODIFIED_DATE DATETIME(6),

    -- Auditoría (Si se desea global, se omiten tenant y external ids)
    INDEX IDX_SPECIALTY_CODE (CODE),
    INDEX IDX_SPECIALTY_NAME (NAME),
    CONSTRAINT CHK_SPECIALTY_TARGET CHECK (TARGET_PROFESSION IN ('DOCTOR', 'NURSE', 'BOTH'))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

COMMIT;
