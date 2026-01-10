-- ============================================================
-- Script: V9_2__create_medical_procedure.sql
-- Módulo: vitalia-medical-catalog
-- Descripción: Creación de la tabla CAT_MEDICAL_PROCEDURE.
-- Autor: Juan Amachi
-- Fecha: 2026-01-04
-- ============================================================

CREATE TABLE IF NOT EXISTS CAT_MEDICAL_PROCEDURE (
    ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    CODE VARCHAR(20) NOT NULL,
    NAME VARCHAR(250) NOT NULL,
    TYPE VARCHAR(50) NOT NULL, -- e.g., LABORATORY, RADIOLOGY, SURGERY
    ACTIVE BOOLEAN NOT NULL DEFAULT TRUE,

    -- Auditoría
    CREATED_BY VARCHAR(100) NOT NULL,
    CREATED_DATE TIMESTAMP NOT NULL,
    LAST_MODIFIED_BY VARCHAR(100),
    LAST_MODIFIED_DATE TIMESTAMP,

    -- ===============================
    -- Restricciones
    -- ===============================
    CONSTRAINT UK_PROCEDURE_CODE UNIQUE (CODE)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
