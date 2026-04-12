-- Script: V5_34__create_med_consultation_type.sql
-- Módulo: vitalia-medical
-- Descripción: Creación de la tabla MED_CONSULTATION_TYPE (SaaS Elite Tier).
-- Autor: Juan Amachi
-- ============================================================

CREATE TABLE IF NOT EXISTS MED_CONSULTATION_TYPE (
    -- ==========================================
    -- Identidad y Auditoría Base
    -- ==========================================
    ID                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    TENANT_ID           VARCHAR(50) NOT NULL,
    EXTERNAL_ID         VARCHAR(36) NOT NULL UNIQUE,
    VERSION             BIGINT DEFAULT 0 NOT NULL,
    IS_DELETED          BOOLEAN DEFAULT FALSE NOT NULL,

    -- ==========================================
    -- Datos del Tipo de Consulta
    -- ==========================================
    NAME                VARCHAR(150) NOT NULL,
    DESCRIPTION         VARCHAR(500),
    FK_ID_SPECIALTY     BIGINT,

    -- ==========================================
    -- Auditoría de Operación
    -- ==========================================
    CREATED_BY          VARCHAR(100) NOT NULL,
    CREATED_DATE        DATETIME(6) NOT NULL,
    LAST_MODIFIED_BY    VARCHAR(100),
    LAST_MODIFIED_DATE  DATETIME(6),

    -- ==========================================
    -- Constraints & Indexes
    -- ==========================================
    CONSTRAINT FK_MED_CONS_TYPE_SPEC FOREIGN KEY (FK_ID_SPECIALTY) REFERENCES MED_SPECIALTY(ID),
    CONSTRAINT UK_MED_CONS_TENANT_NAME UNIQUE (TENANT_ID, NAME),
    
    INDEX IDX_CONS_TYPE_TENANT (TENANT_ID)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
