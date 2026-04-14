-- ============================================================
-- Script: V5_30__create_med_user_education.sql
-- Módulo: vitalia-medical
-- Descripción: Creación de la tabla MED_USER_EDUCATION (SaaS Elite Tier).
-- ============================================================

CREATE TABLE IF NOT EXISTS MED_USER_EDUCATION (
    -- ==========================================
    -- Identidad y Auditoría Base
    -- ==========================================
    ID                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    TENANT_ID           VARCHAR(50) NOT NULL,
    EXTERNAL_ID         VARCHAR(36) NOT NULL UNIQUE,
    VERSION             BIGINT DEFAULT 0 NOT NULL,
    IS_DELETED          BOOLEAN DEFAULT FALSE NOT NULL,

    -- ==========================================
    -- Relaciones
    -- ==========================================
    FK_ID_USERPROFILE   BIGINT NOT NULL,

    -- ==========================================
    -- Datos Académicos
    -- ==========================================
    INSTITUTION         VARCHAR(150) NOT NULL,
    DEGREE              VARCHAR(150) NOT NULL,
    START_DATE          DATE NOT NULL,
    END_DATE            DATE,

    -- ==========================================
    -- Auditoría de Operación
    -- ==========================================
    CREATED_BY          VARCHAR(100) NOT NULL,
    CREATED_DATE        DATETIME(6)  NOT NULL,
    LAST_MODIFIED_BY    VARCHAR(100),
    LAST_MODIFIED_DATE  DATETIME(6),

    CONSTRAINT FK_MED_EDU_PROFILE FOREIGN KEY (FK_ID_USERPROFILE) REFERENCES MED_USER_PROFILE(ID),
    
    INDEX IDX_USR_EDU_TENANT  (TENANT_ID),
    INDEX IDX_USR_EDU_PROFILE (FK_ID_USERPROFILE)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
