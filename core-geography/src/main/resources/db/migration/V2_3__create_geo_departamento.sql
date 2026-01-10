-- ============================================================
-- Script: V1_2__create_departamento.sql
-- Módulo: vitalia-geography
-- Descripción: Creación de la tabla GEO_DEPARTAMENTO con metadatos, auditoría y restricciones.
-- Autor: Juan Amachi
-- Fecha: 2025-11-02
-- Compatibilidad: MySQL 8.0+
-- ============================================================
CREATE TABLE IF NOT EXISTS GEO_DEPARTAMENTO (
    ID BIGINT PRIMARY KEY AUTO_INCREMENT,
    NOMBRE VARCHAR(100) NOT NULL,
    POBLACION INT DEFAULT NULL,
    SUPERFICIE DECIMAL(38,2) DEFAULT NULL,
    FK_ID_COUNTRY BIGINT DEFAULT NULL,

    -- Auditoría
    CREATED_BY VARCHAR(100) NOT NULL,
    CREATED_DATE TIMESTAMP NOT NULL,
    LAST_MODIFIED_BY VARCHAR(100),
    LAST_MODIFIED_DATE TIMESTAMP,

    -- Constraints
    CONSTRAINT FK_DEPARTAMENTO_COUNTRY FOREIGN KEY (FK_ID_COUNTRY) REFERENCES GEO_COUNTRY(ID),
    UNIQUE KEY UK_NOMBRE_DEPARTAMENTO (NOMBRE),
    -- [MODIFICACION ANTIGRAVITY] Indice compuesto para permitir validacion de jerarquia en hijos
    INDEX IDX_DEPT_HIERARCHY (ID, FK_ID_COUNTRY)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
