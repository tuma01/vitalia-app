-- Script: V2_9__create_geo_address.sql
-- Módulo: core-geography
-- Descripción: Creación de la tabla GEO_ADDRESS con auditoría y restricciones de jerarquía.
-- ==============================================

CREATE TABLE IF NOT EXISTS GEO_ADDRESS (
    ID BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    NUMERO VARCHAR(100) comment 'The first line of the GEO_ADDRESS.',
    DIRECCION VARCHAR(100) comment 'The second line of GEO_ADDRESS. It is Optional.',
    BLOQUE VARCHAR(50),
    PISO INT,
    NUMERO_DEPARTAMENTO VARCHAR(20),
    MEDIDOR VARCHAR(20),
    CASILLA_POSTAL VARCHAR(20) comment 'The postal code of the GEO_ADDRESS.',
    CIUDAD VARCHAR(100) comment 'The name City of the GEO_ADDRESS.',
    LOCATION VARCHAR(100) comment 'Geometry data with spatial index.',
    FK_ID_COUNTRY BIGINT,
    FK_ID_DEPARTAMENTO BIGINT,

    -- Auditoría
    CREATED_BY VARCHAR(100) NOT NULL,
    CREATED_DATE TIMESTAMP NOT NULL,
    LAST_MODIFIED_BY VARCHAR(100),
    LAST_MODIFIED_DATE TIMESTAMP,

    -- Constraints
    CONSTRAINT FK_ADDRESS_COUNTRY FOREIGN KEY (FK_ID_COUNTRY) REFERENCES GEO_COUNTRY(ID),

    -- Impide insertar un GEO_DEPARTAMENTO que no pertenezca al Pais indicado
    CONSTRAINT FK_ADDRESS_HIERARCHY FOREIGN KEY (FK_ID_DEPARTAMENTO, FK_ID_COUNTRY) REFERENCES GEO_DEPARTAMENTO(ID, FK_ID_COUNTRY)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
commit;
