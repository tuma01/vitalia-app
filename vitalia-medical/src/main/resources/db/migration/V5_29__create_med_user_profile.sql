-- ============================================================
-- Script: V5_29__create_med_user_profile.sql
-- Módulo: vitalia-medical
-- Descripción: Creación de la tabla MED_USER_PROFILE (SaaS Elite Tier).
-- ============================================================

CREATE TABLE IF NOT EXISTS MED_USER_PROFILE (
    -- ==========================================
    -- Identidad y Auditoría Base
    -- ==========================================
    ID                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    TENANT_ID           VARCHAR(50) NOT NULL,
    EXTERNAL_ID         VARCHAR(36) NOT NULL UNIQUE,
    VERSION             BIGINT DEFAULT 0 NOT NULL,
    IS_DELETED          BOOLEAN DEFAULT FALSE NOT NULL,

    -- ==========================================
    -- Datos Curriculares
    -- ==========================================
    BIOGRAPHY           VARCHAR(2000),
    PHOTO               LONGBLOB,                        -- Binary storage for profile photo

    -- ==========================================
    -- Auditoría de Operación
    -- ==========================================
    CREATED_BY          VARCHAR(100) NOT NULL,
    CREATED_DATE        DATETIME(6)  NOT NULL,
    LAST_MODIFIED_BY    VARCHAR(100),
    LAST_MODIFIED_DATE  DATETIME(6),

    INDEX IDX_USR_PROF_TENANT (TENANT_ID)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
