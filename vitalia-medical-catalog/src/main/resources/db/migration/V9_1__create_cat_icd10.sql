-- ============================================================
-- Script: V9_5__create_cat_icd10.sql
-- Módulo: vitalia-medical-catalog
-- Descripción: Creación de la tabla CAT_ICD10 (Diagnósticos).
-- ============================================================

CREATE TABLE IF NOT EXISTS CAT_ICD10 (
    ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    CODE VARCHAR(20) NOT NULL,
    DESCRIPTION VARCHAR(500) NOT NULL,
    ACTIVE BOOLEAN NOT NULL DEFAULT TRUE,

    -- Auditoría
    CREATED_BY VARCHAR(100) NOT NULL,
    CREATED_DATE TIMESTAMP NOT NULL,
    LAST_MODIFIED_BY VARCHAR(100),
    LAST_MODIFIED_DATE TIMESTAMP,

    -- ===============================
    -- Restricciones
    -- ===============================
    CONSTRAINT UK_ICD10_CODE UNIQUE (CODE)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
