-- ============================================================
-- Script: V9_8__create_medical_specialty.sql
-- Módulo: vitalia-medical-catalog
-- Descripción: Creación de la tabla CAT_MEDICAL_SPECIALTY.
-- Autor: Juan Amachi
-- Fecha: 2026-01-04
-- ============================================================

CREATE TABLE IF NOT EXISTS CAT_MEDICAL_SPECIALTY (
    ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    CODE VARCHAR(20) NOT NULL,
    NAME VARCHAR(150) NOT NULL,
    DESCRIPTION VARCHAR(500),
    TARGET_PROFESSION VARCHAR(20) NOT NULL DEFAULT 'BOTH',
    ACTIVE BOOLEAN NOT NULL DEFAULT TRUE,

    -- Auditoría
    CREATED_BY VARCHAR(100) NOT NULL,
    CREATED_DATE TIMESTAMP NOT NULL,
    LAST_MODIFIED_BY VARCHAR(100),
    LAST_MODIFIED_DATE TIMESTAMP,

    -- ===============================
    -- Restricciones
    -- ===============================
    CONSTRAINT UK_SPECIALTY_CODE UNIQUE (CODE),
    INDEX IX_SPECIALTY_NAME (NAME),
    CONSTRAINT CHK_SPECIALTY_TARGET CHECK (TARGET_PROFESSION IN ('DOCTOR', 'NURSE', 'BOTH'))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
