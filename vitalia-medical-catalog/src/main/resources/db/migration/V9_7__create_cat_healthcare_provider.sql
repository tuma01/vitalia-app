-- ============================================================
-- Script: V9_6__create_healthcare_provider.sql
-- Módulo: vitalia-medical-catalog
-- Descripción: Creación de la tabla CAT_HEALTHCARE_PROVIDER.
-- Autor: Juan Amachi
-- Fecha: 2026-01-04
-- ============================================================

CREATE TABLE IF NOT EXISTS CAT_HEALTHCARE_PROVIDER (
    ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    CODE VARCHAR(20) NOT NULL,
    NAME VARCHAR(250) NOT NULL,
    TAX_ID VARCHAR(50) NOT NULL,
    EMAIL VARCHAR(100),
    PHONE VARCHAR(50),
    ACTIVE BOOLEAN NOT NULL DEFAULT TRUE,

    -- Auditoría
    CREATED_BY VARCHAR(100) NOT NULL,
    CREATED_DATE TIMESTAMP NOT NULL,
    LAST_MODIFIED_BY VARCHAR(100),
    LAST_MODIFIED_DATE TIMESTAMP,

    -- ===============================
    -- Restricciones
    -- ===============================
    CONSTRAINT UK_PROVIDER_CODE UNIQUE (CODE),
    CONSTRAINT UK_PROVIDER_TAX_ID UNIQUE (TAX_ID),
    INDEX IX_PROVIDER_NAME (NAME)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
