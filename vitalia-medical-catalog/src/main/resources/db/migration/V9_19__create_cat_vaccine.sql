-- ============================================================
-- Script: V9_18__create_vaccine.sql
-- Módulo: vitalia-medical-catalog
-- Descripción: Creación de la tabla CAT_VACCINE.
-- Autor: Juan Amachi
-- Fecha: 2026-01-04
-- ============================================================

CREATE TABLE IF NOT EXISTS CAT_VACCINE (
    ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    CODE VARCHAR(20) NOT NULL,
    NAME VARCHAR(150) NOT NULL,
    DESCRIPTION VARCHAR(250),
    ACTIVE BOOLEAN NOT NULL DEFAULT TRUE,

    -- Auditoría
    CREATED_BY VARCHAR(100) NOT NULL,
    CREATED_DATE TIMESTAMP NOT NULL,
    LAST_MODIFIED_BY VARCHAR(100),
    LAST_MODIFIED_DATE TIMESTAMP,

    CONSTRAINT UK_VACCINE_CODE UNIQUE (CODE),
    INDEX IX_VACCINE_NAME (NAME)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
