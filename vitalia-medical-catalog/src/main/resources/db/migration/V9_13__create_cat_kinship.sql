-- ============================================================
-- Script: V9_13__create_cat_kinship.sql
-- Módulo: vitalia-medical-catalog
-- Descripción: Creación de la tabla CAT_KINSHIP (SaaS Elite Tier).
-- Autor: Juan Amachi
-- Fecha: 2026-01-04
-- ============================================================

CREATE TABLE IF NOT EXISTS CAT_KINSHIP (
    ID          BIGINT AUTO_INCREMENT PRIMARY KEY,
    CODE        VARCHAR(20)  NOT NULL,
    NAME        VARCHAR(100) NOT NULL,
    ACTIVE      BOOLEAN      NOT NULL DEFAULT TRUE,

    -- Concurrencia e ID Externo (Tier Elite)
    VERSION BIGINT NOT NULL DEFAULT 0,
    EXTERNAL_ID VARCHAR(36)  NOT NULL UNIQUE,

    -- Auditoría
    CREATED_BY VARCHAR(100) NOT NULL,
    CREATED_DATE DATETIME(6) NOT NULL,
    LAST_MODIFIED_BY VARCHAR(100),
    LAST_MODIFIED_DATE DATETIME(6),

    -- Constraints
    CONSTRAINT UK_KINSHIP_EXTERNAL_ID  UNIQUE (EXTERNAL_ID),
    CONSTRAINT UK_KINSHIP_CODE UNIQUE (CODE),
    INDEX IDX_KINSHIP_CODE   (CODE)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
