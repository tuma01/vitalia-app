-- ============================================================
-- Script: V1_4__create_provincia.sql
-- Módulo: vitalia-geography
-- Descripción: Creación de la tabla GEO_PROVINCIA con restricciones de jerarquía.
-- ============================================================

CREATE TABLE IF NOT EXISTS GEO_PROVINCIA (
    ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    NOMBRE VARCHAR(100) NOT NULL,
    POBLACION INT,
    SUPERFICIE DECIMAL(15,2),

    FK_ID_DEPARTAMENTO BIGINT NOT NULL,

    -- Auditoría
    CREATED_BY VARCHAR(100) NOT NULL,
    CREATED_DATE TIMESTAMP NOT NULL,
    LAST_MODIFIED_BY VARCHAR(100),
    LAST_MODIFIED_DATE TIMESTAMP,

    -- Constraints
    CONSTRAINT UK_NOMBRE_PROVINCIA UNIQUE (NOMBRE),
    CONSTRAINT FK_PROVINCIA_DEPARTAMENTO FOREIGN KEY (FK_ID_DEPARTAMENTO) REFERENCES GEO_DEPARTAMENTO (ID) ON DELETE CASCADE

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
