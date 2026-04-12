-- Script: V5_16__create_med_physiological_habit.sql
-- Módulo: vitalia-medical
-- Descripción: Creación de la tabla MED_PHYSIOLOGICAL_HABIT (SaaS Elite Tier).
-- Autor: Juan Amachi
-- ============================================================

CREATE TABLE IF NOT EXISTS MED_PHYSIOLOGICAL_HABIT (
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
    FK_ID_MEDICAL_HISTORY BIGINT NOT NULL,

    -- ==========================================
    -- Datos Clínicos (English Standard)
    -- ==========================================
    IS_CURRENT          BOOLEAN DEFAULT TRUE NOT NULL,
    NUTRITION           VARCHAR(500),
    SLEEP_QUALITY       VARCHAR(30),
    SLEEP_HOURS         INT,
    URINATION           VARCHAR(250),
    DEFECATION          VARCHAR(250),
    SEXUALITY           VARCHAR(250),
    SPORTS_ACTIVITY     VARCHAR(500),
    OTHERS              VARCHAR(500),

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
    CONSTRAINT FK_MED_HABPHY_HISTORY FOREIGN KEY (FK_ID_MEDICAL_HISTORY) REFERENCES MED_MEDICAL_HISTORY(ID),
    
    INDEX IDX_PHY_HAB_TENANT  (TENANT_ID),
    INDEX IDX_PHY_HAB_HISTORY (FK_ID_MEDICAL_HISTORY),
    CONSTRAINT UK_PHYHAB_TENANT_HISTORY UNIQUE (TENANT_ID, FK_ID_MEDICAL_HISTORY, IS_CURRENT)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
