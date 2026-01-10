-- Script: V2_1__create_geo_country.sql
-- Módulo: core-geography
-- Descripción: Creación de la tabla GEO_COUNTRY con metadatos, auditoría y restricciones.
-- Autor: Juan Amachi
-- Fecha: 2025-11-02
-- Compatibilidad: MySQL 8.0+
-- ============================================================

CREATE TABLE IF NOT EXISTS GEO_COUNTRY (
    ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    ISO VARCHAR(2) NOT NULL,
    NAME VARCHAR(100) NOT NULL,
    NICE_NAME VARCHAR(100) NOT NULL,
    ISO3 VARCHAR(3),
    NUM_CODE INT,
    PHONE_CODE INT NOT NULL,

    -- Auditoría
    CREATED_BY VARCHAR(100) NOT NULL,
    CREATED_DATE TIMESTAMP NOT NULL,
    LAST_MODIFIED_BY VARCHAR(100),
    LAST_MODIFIED_DATE TIMESTAMP,

    -- Constraints
    CONSTRAINT UK_COUNTRY_ISO UNIQUE (ISO),
    CONSTRAINT UK_COUNTRY_NAME UNIQUE (NAME)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;