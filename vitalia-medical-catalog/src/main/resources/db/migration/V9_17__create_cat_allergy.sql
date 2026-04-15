-- Script: V9_17__create_cat_allergy.sql
-- Módulo: vitalia-medical-catalog
-- Descripción: Creación de la tabla CAT_ALLERGY (POOL GLOBAL PURO).
-- ============================================================

CREATE TABLE IF NOT EXISTS CAT_ALLERGY (
    ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    CODE VARCHAR(50) NOT NULL UNIQUE,
    NAME VARCHAR(150) NOT NULL UNIQUE,
    TYPE VARCHAR(50) NOT NULL,            -- DRUG, FOOD, ENVIRONMENTAL, etc.
    DESCRIPTION TEXT,

    -- Concurrencia e ID Externo (Tier Elite)
    VERSION BIGINT NOT NULL DEFAULT 0,
    EXTERNAL_ID VARCHAR(36)  NOT NULL UNIQUE,

    -- Auditoría
    CREATED_BY VARCHAR(100) NOT NULL,
    CREATED_DATE DATETIME(6) NOT NULL,
    LAST_MODIFIED_BY VARCHAR(100),
    LAST_MODIFIED_DATE DATETIME(6),

    INDEX IDX_ALLERGY_NAME (NAME)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

COMMIT;
