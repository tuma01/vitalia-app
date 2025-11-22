-- ============================================================
-- Script: V2_0_0__create_country.sql
-- Módulo: vitalia-geography
-- Descripción: Creación de la tabla COUNTRY con metadatos, auditoría y restricciones.
-- Autor: Juan Amachi
-- Fecha: 2025-11-02
-- Compatibilidad: MySQL 8.0+
-- ============================================================

-- Eliminar tabla si existiera (solo en desarrollo o migraciones controladas)
DROP TABLE IF EXISTS COUNTRY;

-- ============================================
-- Creación de la tabla COUNTRY
-- ============================================
CREATE TABLE COUNTRY (
    ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    ISO CHAR(2) NOT NULL,
    NAME VARCHAR(100) NOT NULL,
    NICE_NAME VARCHAR(100) NOT NULL,
    ISO3 CHAR(3),
    NUM_CODE INT,
    PHONE_CODE INT NOT NULL,

    -- ===============================
    -- Auditoría
    -- ===============================
    CREATED_BY VARCHAR(100) NOT NULL,
    CREATED_DATE TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    LAST_MODIFIED_BY VARCHAR(100),
    LAST_MODIFIED_DATE TIMESTAMP DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,

    -- ===============================
    -- Restricciones de unicidad
    -- ===============================
    CONSTRAINT UK_COUNTRY_ISO UNIQUE (ISO),
    CONSTRAINT UK_COUNTRY_NAME UNIQUE (NAME)
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci;
