-- ============================================================
-- Script: V9_4__create_medication.sql
-- Módulo: vitalia-medical-catalog
-- Descripción: Creación de la tabla CAT_MEDICATION.
-- Autor: Juan Amachi
-- Fecha: 2026-01-04
-- ============================================================

CREATE TABLE IF NOT EXISTS CAT_MEDICATION (
    ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    CODE VARCHAR(20) NOT NULL,
    GENERIC_NAME VARCHAR(250) NOT NULL,
    COMMERCIAL_NAME VARCHAR(250),
    CONCENTRATION VARCHAR(100),
    PHARMACEUTICAL_FORM VARCHAR(100),
    PRESENTATION VARCHAR(250),
    ACTIVE BOOLEAN NOT NULL DEFAULT TRUE,

    -- Auditoría
    CREATED_BY VARCHAR(100) NOT NULL,
    CREATED_DATE TIMESTAMP NOT NULL,
    LAST_MODIFIED_BY VARCHAR(100),
    LAST_MODIFIED_DATE TIMESTAMP,

    -- ===============================
    -- Restricciones
    -- ===============================
    CONSTRAINT UK_MEDICATION_CODE UNIQUE (CODE),
    INDEX IX_MEDICATION_GENERIC (GENERIC_NAME)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
