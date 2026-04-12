-- Script: V5_5__create_med_bed.sql
-- Módulo: vitalia-medical
-- Descripción: Creación de la tabla MED_BED (SaaS Elite Tier).
-- Autor: Juan Amachi
-- ============================================================

CREATE TABLE IF NOT EXISTS MED_BED (
    -- ==========================================
    -- Identidad y Auditoría Base
    -- ==========================================
    ID                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    TENANT_ID           VARCHAR(50) NOT NULL,
    EXTERNAL_ID         VARCHAR(36) NOT NULL UNIQUE,
    VERSION             BIGINT DEFAULT 0 NOT NULL,
    IS_DELETED          BOOLEAN DEFAULT FALSE NOT NULL,

    -- ==========================================
    -- Datos de la Cama
    -- ==========================================
    FK_ID_ROOM              BIGINT NOT NULL,             -- Habitación contenedora
    FK_ID_HOSPITALIZATION   BIGINT,                      -- Referencia ocupación activa
    BED_CODE                VARCHAR(50) NOT NULL,        -- Identificador (C01A)
    BED_NUMBER              VARCHAR(50) NOT NULL,        -- Rotulación física
    BED_STATUS              VARCHAR(30) DEFAULT 'AVAILABLE',
    IS_OCCUPIED             BOOLEAN DEFAULT FALSE,
    DESCRIPTION             VARCHAR(500),
    MAINTENANCE_DUE         DATE,
    IS_ACTIVE               BOOLEAN DEFAULT TRUE,

    -- ==========================================
    -- Auditoría de Operación
    -- ==========================================
    CREATED_BY              VARCHAR(100) NOT NULL,
    CREATED_DATE            DATETIME(6) NOT NULL,
    LAST_MODIFIED_BY        VARCHAR(100),
    LAST_MODIFIED_DATE      DATETIME(6),

    -- ==========================================
    -- Constraints & Indexes
    -- ==========================================
    CONSTRAINT FK_MED_BED_ROOM FOREIGN KEY (FK_ID_ROOM) REFERENCES MED_ROOM(ID),
    CONSTRAINT FK_MED_BED_HOSPITALIZATION FOREIGN KEY (FK_ID_HOSPITALIZATION) REFERENCES MED_HOSPITALIZATION(ID),

    INDEX IDX_BED_TENANT (TENANT_ID),
    INDEX IDX_BED_ROOM   (FK_ID_ROOM),
    INDEX IDX_BED_STATUS (BED_STATUS),
    CONSTRAINT UK_BED_TENANT_ROOM_CODE UNIQUE (TENANT_ID, FK_ID_ROOM, BED_CODE)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
