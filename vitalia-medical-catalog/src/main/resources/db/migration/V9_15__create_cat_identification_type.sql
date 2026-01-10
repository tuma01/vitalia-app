-- ============================================================
-- Script: V9_16__insert_cat_identification_type.sql
-- Módulo: vitalia-medical-catalog
-- Descripción: Inserción de tipos de documento oficiales.
-- ============================================================

CREATE TABLE IF NOT EXISTS CAT_IDENTIFICATION_TYPE (
    ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    CODE VARCHAR(20) NOT NULL,
    NAME VARCHAR(100) NOT NULL,
    ACTIVE BOOLEAN NOT NULL DEFAULT TRUE,

    -- Auditoría
    CREATED_BY VARCHAR(100) NOT NULL,
    CREATED_DATE TIMESTAMP NOT NULL,
    LAST_MODIFIED_BY VARCHAR(100),
    LAST_MODIFIED_DATE TIMESTAMP,

    CONSTRAINT UK_ID_TYPE_CODE UNIQUE (CODE)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
