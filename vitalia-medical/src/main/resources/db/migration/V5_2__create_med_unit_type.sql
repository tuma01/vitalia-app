-- Script: V5_2__create_med_unit_type.sql
-- Módulo: vitalia-medical
-- Descripción: Creación de la tabla MED_DEPARTMENT_UNIT_TYPE (SaaS Elite Tier).
-- Autor: Juan Amachi
-- ============================================================

CREATE TABLE IF NOT EXISTS MED_DEPARTMENT_UNIT_TYPE (
    -- ==========================================
    -- Identidad y Auditoría Base
    -- ==========================================
    ID                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    TENANT_ID           VARCHAR(50) NOT NULL,
    EXTERNAL_ID         VARCHAR(36) NOT NULL UNIQUE,
    VERSION             BIGINT DEFAULT 0 NOT NULL,
    IS_DELETED          BOOLEAN DEFAULT FALSE NOT NULL,

    -- ==========================================
    -- Datos del Tipo de Unidad
    -- ==========================================
    NAME                VARCHAR(100) NOT NULL,           -- Ej: PEDIATRIA, UCI
    DESCRIPTION         VARCHAR(500),

    -- ==========================================
    -- Auditoría de Operación (DATETIME 6 para precisión HIS)
    -- ==========================================
    CREATED_BY          VARCHAR(100) NOT NULL,
    CREATED_DATE        DATETIME(6) NOT NULL,
    LAST_MODIFIED_BY    VARCHAR(100),
    LAST_MODIFIED_DATE  DATETIME(6),

    -- ==========================================
    -- Índices y Constraints
    -- ==========================================
    INDEX IDX_DUT_TENANT (TENANT_ID),
    INDEX IDX_DUT_NAME   (NAME),
    CONSTRAINT UK_DUT_TENANT_NAME UNIQUE (TENANT_ID, NAME)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
