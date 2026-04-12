-- ============================================================
-- Script: V9_11__create_cat_blood_type.sql
-- Módulo: vitalia-medical-catalog
-- Descripción: Creación de la tabla CAT_BLOOD_TYPE (SaaS Elite Tier).
-- Autor: Juan Amachi
-- Fecha: 2026-01-04
-- ============================================================

CREATE TABLE IF NOT EXISTS CAT_BLOOD_TYPE (
    ID          BIGINT AUTO_INCREMENT PRIMARY KEY,
    CODE        VARCHAR(20)  NOT NULL,
    NAME        VARCHAR(50)  NOT NULL,
    ACTIVE      BOOLEAN      NOT NULL DEFAULT TRUE,

    -- Concurrencia e ID Externo (Tier Elite)
    VERSION BIGINT NOT NULL DEFAULT 0,
    EXTERNAL_ID VARCHAR(36)  NOT NULL UNIQUE,

    -- Auditoría
    CREATED_BY VARCHAR(100) NOT NULL,
    CREATED_DATE TIMESTAMP NOT NULL,
    LAST_MODIFIED_BY VARCHAR(100),
    LAST_MODIFIED_DATE TIMESTAMP,

    -- Constraints
    CONSTRAINT UK_BLOOD_TYPE_EXTERNAL_ID  UNIQUE (EXTERNAL_ID),
    CONSTRAINT UK_BLOOD_TYPE_CODE  UNIQUE (CODE),
    INDEX IDX_BLOOD_NAME   (NAME)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
