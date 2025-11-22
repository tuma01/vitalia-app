-- ============================================================
-- Script: V1_4_0__create_country.sql
-- Módulo: vitalia-geography
-- Descripción: Creación de la tabla PROVINCIA con metadatos, auditoría y restricciones.
-- Autor: Juan Amachi
-- Fecha: 2025-11-02
-- Compatibilidad: MySQL 8.0+
-- ============================================================

DROP TABLE IF EXISTS PROVINCIA;

CREATE TABLE PROVINCIA (
    ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    NOMBRE VARCHAR(100) NOT NULL,
    POBLACION INT,
    SUPERFICIE DECIMAL(15,2),

    FK_ID_DEPARTAMENTO BIGINT NOT NULL,

    -- ===============================
    -- Auditoría
    -- ===============================
    CREATED_BY VARCHAR(100) NOT NULL,
    CREATED_DATE TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    LAST_MODIFIED_BY VARCHAR(100) null,
    LAST_MODIFIED_DATE TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,

    -- Constraints
    CONSTRAINT UK_NOMBRE_PROVINCIA UNIQUE (NOMBRE),
    CONSTRAINT FK_PROVINCIA_DEPARTAMENTO FOREIGN KEY (FK_ID_DEPARTAMENTO)
        REFERENCES DEPARTAMENTO (ID) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

