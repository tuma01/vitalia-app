-- Script: V5_36__create_med_staff_specialities.sql
-- Módulo: vitalia-medical
-- Descripción: Creación de catálogos de especialidades profesionales (SaaS Elite Tier).
-- Autor: Juan Amachi
-- ============================================================

-- 1. Especialidades Médicas Profesionales
CREATE TABLE IF NOT EXISTS MED_DOCTOR_PROF_SPECIALITY (
    ID                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    TENANT_ID           VARCHAR(50) NOT NULL,
    EXTERNAL_ID         VARCHAR(36) NOT NULL UNIQUE,
    VERSION             BIGINT DEFAULT 0 NOT NULL,
    IS_DELETED          BOOLEAN DEFAULT FALSE NOT NULL,
    NAME                VARCHAR(150) NOT NULL,
    DESCRIPTION         VARCHAR(500),
    CREATED_BY          VARCHAR(100) NOT NULL,
    CREATED_DATE        DATETIME(6) NOT NULL,
    LAST_MODIFIED_BY    VARCHAR(100),
    LAST_MODIFIED_DATE  DATETIME(6),
    CONSTRAINT UK_MED_DOC_SPEC UNIQUE (TENANT_ID, NAME),
    INDEX IDX_DOC_SPEC_TENANT (TENANT_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 2. Especialidades de Enfermería Profesionales
CREATE TABLE IF NOT EXISTS MED_NURSE_PROF_SPECIALITY (
    ID                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    TENANT_ID           VARCHAR(50) NOT NULL,
    EXTERNAL_ID         VARCHAR(36) NOT NULL UNIQUE,
    VERSION             BIGINT DEFAULT 0 NOT NULL,
    IS_DELETED          BOOLEAN DEFAULT FALSE NOT NULL,
    NAME                VARCHAR(150) NOT NULL,
    DESCRIPTION         VARCHAR(500),
    CREATED_BY          VARCHAR(100) NOT NULL,
    CREATED_DATE        DATETIME(6) NOT NULL,
    LAST_MODIFIED_BY    VARCHAR(100),
    LAST_MODIFIED_DATE  DATETIME(6),
    CONSTRAINT UK_MED_NUR_SPEC UNIQUE (TENANT_ID, NAME),
    INDEX IDX_NUR_SPEC_TENANT (TENANT_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
