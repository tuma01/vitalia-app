-- ============================================================
-- Script: V9_1__create_cat_icd10.sql
-- Módulo: vitalia-medical-catalog
-- Descripción: Creación de la tabla CAT_ICD10 (Diagnósticos - SaaS Elite Tier).
-- ============================================================

CREATE TABLE IF NOT EXISTS CAT_ICD10 (
    ID          BIGINT AUTO_INCREMENT PRIMARY KEY,
    TENANT_ID   VARCHAR(50)  NOT NULL,
    EXTERNAL_ID VARCHAR(36)  NOT NULL,
    CODE        VARCHAR(20)  NOT NULL,
    DESCRIPTION VARCHAR(500) NOT NULL,
    ACTIVE      BOOLEAN      NOT NULL DEFAULT TRUE,
    VERSION     BIGINT       NOT NULL DEFAULT 0,

    -- Auditoría
    CREATED_BY        VARCHAR(100) NOT NULL,
    CREATED_DATE      TIMESTAMP    NOT NULL,
    LAST_MODIFIED_BY  VARCHAR(100),
    LAST_MODIFIED_DATE TIMESTAMP,

    -- Constraints
    CONSTRAINT UK_ICD10_EXTERNAL_ID UNIQUE (EXTERNAL_ID),
    CONSTRAINT UK_ICD10_CODE_TENANT UNIQUE (CODE, TENANT_ID),
    INDEX IDX_ICD10_TENANT (TENANT_ID),
    INDEX IDX_ICD10_CODE   (CODE)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
