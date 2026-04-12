-- Script: V5_33__create_med_user_conference.sql
-- Módulo: vitalia-medical
-- Descripción: Creación de la tabla MED_USER_CONFERENCE (SaaS Elite Tier).
-- Autor: Juan Amachi
-- ============================================================

CREATE TABLE IF NOT EXISTS MED_USER_CONFERENCE (
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
    -- Datos del Evento
    -- ==========================================
    TOPIC               VARCHAR(150) NOT NULL,
    DESCRIPTION         VARCHAR(500),
    ORGANIZER           VARCHAR(150),
    LOCATION            VARCHAR(255),
    IS_INTERNATIONAL    BOOLEAN DEFAULT FALSE NOT NULL,
    CONFERENCE_DATE     DATE NOT NULL,

    -- ==========================================
    -- Auditoría de Operación
    -- ==========================================
    CREATED_BY          VARCHAR(100) NOT NULL,
    CREATED_DATE        DATETIME(6) NOT NULL,
    LAST_MODIFIED_BY    VARCHAR(100),
    LAST_MODIFIED_DATE  DATETIME(6),

    CONSTRAINT FK_MED_CONF_PROFILE FOREIGN KEY (FK_ID_USERPROFILE) REFERENCES MED_USER_PROFILE(ID),
    
    INDEX IDX_USR_CONF_TENANT  (TENANT_ID),
    INDEX IDX_USR_CONF_PROFILE (FK_ID_USERPROFILE)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
