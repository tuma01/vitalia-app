-- ============================================================
-- Script: V9_17__create_cat_allergy.sql
-- Módulo: vitalia-medical-catalog
-- Descripción: Creación de la tabla CAT_ALLERGY (SaaS Elite Tier).
-- Autor: Juan Amachi
-- Fecha: 2026-01-04
-- ============================================================

CREATE TABLE IF NOT EXISTS CAT_ALLERGY (
    ID          BIGINT AUTO_INCREMENT PRIMARY KEY,
    TENANT_ID   VARCHAR(50)  NOT NULL,
    EXTERNAL_ID VARCHAR(36)  NOT NULL,
    CODE        VARCHAR(20)  NOT NULL,
    NAME        VARCHAR(150) NOT NULL,
    TYPE        VARCHAR(50)  NOT NULL,
    DESCRIPTION VARCHAR(250),
    ACTIVE      BOOLEAN      NOT NULL DEFAULT TRUE,
    VERSION     BIGINT       NOT NULL DEFAULT 0,

    -- Auditoría
    CREATED_BY        VARCHAR(100) NOT NULL,
    CREATED_DATE      TIMESTAMP    NOT NULL,
    LAST_MODIFIED_BY  VARCHAR(100),
    LAST_MODIFIED_DATE TIMESTAMP,

    -- Constraints
    CONSTRAINT UK_ALLERGY_EXTERNAL_ID    UNIQUE (EXTERNAL_ID),
    CONSTRAINT UK_ALLERGY_CODE_TENANT    UNIQUE (CODE, TENANT_ID),
    INDEX IDX_ALLERGY_TENANT (TENANT_ID),
    INDEX IDX_ALLERGY_NAME   (NAME),
    CONSTRAINT CHK_ALLERGY_TYPE CHECK (TYPE IN ('DRUG', 'FOOD', 'ENVIRONMENTAL', 'OTHER'))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
