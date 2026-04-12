-- Script: V5_26__create_med_episode_of_care.sql
-- Módulo: vitalia-medical
-- Descripción: Creación de la tabla MED_EPISODE_OF_CARE (SaaS Elite Tier).
-- Autor: Juan Amachi
-- ============================================================

CREATE TABLE IF NOT EXISTS MED_EPISODE_OF_CARE (
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
    FK_ID_PATIENT       BIGINT NOT NULL,
    FK_ID_MANAGING_PRACTITIONER BIGINT NOT NULL,

    -- ==========================================
    -- Datos del Episodio (FHIR)
    -- ==========================================
    STATUS              VARCHAR(30) NOT NULL,            -- PLANNED, ACTIVE, ONHOLD, FINISHED, CANCELLED
    TYPE                VARCHAR(100),                    -- Cuidado continuo (Embarazo, etc.)
    PERIOD_START        DATETIME(6) NOT NULL,
    PERIOD_END          DATETIME(6),
    GOALS               TEXT,
    NOTES               TEXT,

    -- ==========================================
    -- Auditoría de Operación
    -- ==========================================
    CREATED_BY          VARCHAR(100) NOT NULL,
    CREATED_DATE        DATETIME(6) NOT NULL,
    LAST_MODIFIED_BY    VARCHAR(100),
    LAST_MODIFIED_DATE  DATETIME(6),

    -- ==========================================
    -- Constraints & Indexes
    -- ==========================================
    CONSTRAINT FK_MED_EPI_PATIENT FOREIGN KEY (FK_ID_PATIENT) REFERENCES MED_PATIENT(ID),
    CONSTRAINT FK_MED_EPI_DOC     FOREIGN KEY (FK_ID_MANAGING_PRACTITIONER) REFERENCES MED_DOCTOR(ID),
    
    INDEX IDX_EPI_TENANT  (TENANT_ID),
    INDEX IDX_EPI_PATIENT (FK_ID_PATIENT),
    INDEX IDX_EPI_STATUS  (STATUS)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
