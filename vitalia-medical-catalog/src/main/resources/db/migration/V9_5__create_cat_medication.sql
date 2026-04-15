-- Script: V9_5__create_cat_medication.sql
-- Módulo: vitalia-medical-catalog
-- Descripción: Creación de la tabla CAT_MEDICATION como catálogo GLOBAL AUDITABLE.
-- ============================================================

CREATE TABLE IF NOT EXISTS CAT_MEDICATION (
    ID                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    CODE                VARCHAR(50) NOT NULL UNIQUE,
    GENERIC_NAME        VARCHAR(250) NOT NULL,
    COMMERCIAL_NAME     VARCHAR(250),
    CONCENTRATION       VARCHAR(100),
    PHARMACEUTICAL_FORM VARCHAR(100),
    PRESENTATION        VARCHAR(250),
    DESCRIPTION         TEXT,

    -- Concurrencia e ID Externo (Tier Elite)
    VERSION             BIGINT NOT NULL DEFAULT 0,
    EXTERNAL_ID         VARCHAR(36) NOT NULL UNIQUE,

    -- Auditoría Global
    CREATED_BY          VARCHAR(100) NOT NULL,
    CREATED_DATE        DATETIME(6)  NOT NULL,
    LAST_MODIFIED_BY    VARCHAR(100),
    LAST_MODIFIED_DATE  DATETIME(6),

    INDEX IDX_MEDICATION_CODE (CODE)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

COMMIT;
