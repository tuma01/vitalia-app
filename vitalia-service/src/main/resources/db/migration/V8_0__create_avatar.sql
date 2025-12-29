-- ============================================================
-- Script: V2_0_0__create_avatar.sql
-- Módulo: vitalia-service
-- Descripción: Creación de la tabla AVATAR con metadatos, auditoría y restricciones.
-- Autor: Juan Amachi
-- Fecha: 2025-11-02
-- Compatibilidad: MySQL 8.0+
-- ============================================================

-- ============================================
-- Creación de la tabla AVATAR
-- ============================================
CREATE TABLE IF NOT EXISTS AVATAR (
    ID BIGINT PRIMARY KEY AUTO_INCREMENT,
    USER_ID BIGINT NOT NULL,
    TENANT_CODE VARCHAR(100) NOT NULL,
    FILENAME VARCHAR(255),
    MIME_TYPE VARCHAR(50),
    CONTENT LONGBLOB NOT NULL,
    SIZE BIGINT,
    -- ===============================
    -- Auditoría
    -- ===============================
    CREATED_BY VARCHAR(100),
    CREATED_DATE TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    LAST_MODIFIED_BY VARCHAR(100),
    LAST_MODIFIED_DATE TIMESTAMP DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,

    -- ===============================
    -- Restricciones de unicidad
    -- ===============================
    CONSTRAINT FK_AVATAR_USER FOREIGN KEY (USER_ID) REFERENCES USER(id) ON DELETE CASCADE,
    CONSTRAINT FK_AVATAR_TENANT FOREIGN KEY (TENANT_CODE) REFERENCES TENANT(CODE) ON DELETE CASCADE
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- Índices
-- ============================================
CREATE UNIQUE INDEX IDX_AVATAR_USER_TENANT ON AVATAR(USER_ID, TENANT_CODE);
CREATE INDEX IDX_AVATAR_USER ON AVATAR(USER_ID);
CREATE INDEX IDX_AVATAR_TENANT ON AVATAR(TENANT_CODE);
