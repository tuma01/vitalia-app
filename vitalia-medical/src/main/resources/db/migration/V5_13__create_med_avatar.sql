-- Script: V5_13__create_med_avatar.sql
-- Módulo: vitalia-medical
-- Descripción: Creación de la tabla MED_AVATAR (SaaS Elite Tier).
-- Autor: Juan Amachi
-- ============================================================

CREATE TABLE IF NOT EXISTS MED_AVATAR (
    -- ==========================================
    -- Identidad y Auditoría Base
    -- ==========================================
    ID                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    TENANT_ID           VARCHAR(50) NOT NULL,
    EXTERNAL_ID         VARCHAR(36) NOT NULL UNIQUE,
    VERSION             BIGINT DEFAULT 0 NOT NULL,
    IS_DELETED          BOOLEAN DEFAULT FALSE NOT NULL,

    -- ==========================================
    -- Datos del Recurso Multimedia
    -- ==========================================
    USER_ID             BIGINT NOT NULL,
    FILENAME            VARCHAR(255),
    MIME_TYPE           VARCHAR(100),
    CONTENT             LONGBLOB,
    CONTENT_SIZE        BIGINT,

    -- ==========================================
    -- Auditoría de Operación
    -- ==========================================
    CREATED_BY          VARCHAR(100) NOT NULL,
    CREATED_DATE        DATETIME(6) NOT NULL,
    LAST_MODIFIED_BY    VARCHAR(100),
    LAST_MODIFIED_DATE  DATETIME(6),

    INDEX IDX_AVATAR_USER   (USER_ID),
    INDEX IDX_AVATAR_TENANT (TENANT_ID)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
