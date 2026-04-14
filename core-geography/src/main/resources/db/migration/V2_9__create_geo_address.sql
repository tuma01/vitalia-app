-- Script: V2_9__create_geo_address.sql
-- Módulo: core-geography
-- Descripción: Creación de la tabla GEO_ADDRESS con auditoría y restricciones de jerarquía.
-- ==============================================
CREATE TABLE IF NOT EXISTS GEO_ADDRESS (
    ID BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    STREET_NUMBER VARCHAR(100) comment 'Street number of the address.',
    STREET_NAME VARCHAR(100) comment 'Street name or full address line.',
    BLOCK VARCHAR(50) comment 'Block or building identifier.',
    FLOOR INT comment 'Floor number.',
    APARTMENT_NUMBER VARCHAR(20) comment 'Apartment or suite number.',
    METER_NUMBER VARCHAR(20) comment 'Utility meter number.',
    ZIP_CODE VARCHAR(20) comment 'The postal code of the address.',
    CITY VARCHAR(100) comment 'The name City of the address.',
    LOCATION VARCHAR(100) comment 'Geometry data or coordinates.',
    
    FK_ID_COUNTRY BIGINT,
    FK_ID_STATE BIGINT,
    FK_ID_PROVINCE BIGINT,
    FK_ID_MUNICIPALITY BIGINT,

    -- Concurrencia e ID Externo (Tier Elite)
    VERSION BIGINT NOT NULL DEFAULT 0,
    EXTERNAL_ID VARCHAR(36) NOT NULL UNIQUE,

    -- Auditoría
    CREATED_BY          VARCHAR(100) NOT NULL,
    CREATED_DATE        DATETIME(6)  NOT NULL,
    LAST_MODIFIED_BY    VARCHAR(100),
    LAST_MODIFIED_DATE  DATETIME(6),

    -- Constraints
    CONSTRAINT FK_ADDRESS_COUNTRY FOREIGN KEY (FK_ID_COUNTRY) REFERENCES GEO_COUNTRY(ID),
    CONSTRAINT FK_ADDRESS_STATE FOREIGN KEY (FK_ID_STATE) REFERENCES GEO_STATE(ID),
    CONSTRAINT FK_ADDRESS_PROVINCE FOREIGN KEY (FK_ID_PROVINCE) REFERENCES GEO_PROVINCE(ID),
    CONSTRAINT FK_ADDRESS_MUNICIPALITY FOREIGN KEY (FK_ID_MUNICIPALITY) REFERENCES GEO_MUNICIPALITY(ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
