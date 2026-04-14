-- ============================================================
-- Script: V2_5__create_geo_province.sql
-- Módulo: core-geography
-- Descripción: Creación de la tabla GEO_PROVINCE con restricciones de jerarquía.
-- ============================================================
CREATE TABLE IF NOT EXISTS GEO_PROVINCE (
    ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    NAME VARCHAR(100) NOT NULL,
    POPULATION INT,
    SURFACE DECIMAL(15,2),

    FK_ID_STATE BIGINT NOT NULL,

    -- Concurrencia e ID Externo (Tier Elite)
    VERSION BIGINT NOT NULL DEFAULT 0,
    EXTERNAL_ID VARCHAR(36) NOT NULL UNIQUE,

    -- Auditoría
    CREATED_BY          VARCHAR(100) NOT NULL,
    CREATED_DATE        DATETIME(6)  NOT NULL,
    LAST_MODIFIED_BY    VARCHAR(100),
    LAST_MODIFIED_DATE  DATETIME(6),

    -- Constraints
    CONSTRAINT UK_PROVINCE_NAME_STATE UNIQUE (NAME, FK_ID_STATE),
    CONSTRAINT FK_PROVINCE_STATE FOREIGN KEY (FK_ID_STATE) REFERENCES GEO_STATE (ID) ON DELETE CASCADE

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
