-- ============================================================
-- Script: V5_5__create_med_bed.sql
-- Módulo: vitalia-medical
-- Descripción: Creación de la tabla MED_BED (SaaS Elite Tier).
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
    -- Jerarquía
    -- ==========================================
    FK_ID_ROOM          BIGINT NOT NULL,

    -- ==========================================
    -- Datos de la Cama
    -- ==========================================
    NAME                VARCHAR(50) NOT NULL,
    CODE                VARCHAR(30) NOT NULL,
    BED_STATUS          VARCHAR(30) NOT NULL,            -- AVAILABLE, OCCUPIED, CLEANING, MAINTENANCE
    IS_ACTIVE           BOOLEAN DEFAULT TRUE,

    -- ==========================================
    -- Auditoría de Operación
    -- ==========================================
    CREATED_BY          VARCHAR(100) NOT NULL,
    CREATED_DATE        DATETIME(6)  NOT NULL,
    LAST_MODIFIED_BY    VARCHAR(100),
    LAST_MODIFIED_DATE  DATETIME(6),

    -- ==========================================
    -- Constraints & Indexes
    -- ==========================================
    CONSTRAINT FK_BED_ROOM FOREIGN KEY (FK_ID_ROOM) REFERENCES MED_ROOM(ID),

    INDEX IDX_BED_TENANT (TENANT_ID),
    CONSTRAINT UK_BED_TENANT_CODE UNIQUE (TENANT_ID, CODE)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
