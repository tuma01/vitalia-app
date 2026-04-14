-- ============================================================
-- Script: V5_28__create_med_professional_info.sql
-- Módulo: vitalia-medical
-- Descripción: Creación de la tabla MED_PROFESSIONAL_INFO (SaaS Elite Tier).
-- ============================================================

CREATE TABLE IF NOT EXISTS MED_PROFESSIONAL_INFO (
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
    FK_ID_PERSON        BIGINT NOT NULL,
    FK_ID_ORGANIZATION  BIGINT,

    -- ==========================================
    -- Datos Laborales / Trayectoria
    -- ==========================================
    PERIOD_START_DATE   DATE NOT NULL,
    PERIOD_END_DATE     DATE,
    IS_CURRENT          BOOLEAN DEFAULT TRUE NOT NULL,
    ROLE_CONTEXT        VARCHAR(50) NOT NULL,            -- CLINICAL, ADMINISTRATIVE, RESEARCH
    POSITION            VARCHAR(100),
    DEPARTMENT          VARCHAR(100),
    EMPLOYEE_ID         VARCHAR(50),
    LICENSE_NUMBER      VARCHAR(100),
    EXPERIENCE_AT_START INT,
    CONTRACT_TYPE       VARCHAR(50),
    WORK_SCHEDULE       VARCHAR(100),
    SALARY_GRADE        VARCHAR(50),
    SUPERVISOR          VARCHAR(100),
    PERFORMANCE_RATING  DECIMAL(3,1),
    NOTES               TEXT,

    -- ==========================================
    -- Auditoría de Operación
    -- ==========================================
    CREATED_BY          VARCHAR(100) NOT NULL,
    CREATED_DATE        DATETIME(6)  NOT NULL,
    LAST_MODIFIED_BY    VARCHAR(100),
    LAST_MODIFIED_DATE  DATETIME(6),

    -- ==========================================
    -- Constraints & Indexes
    -- ==========================================
    CONSTRAINT FK_MED_PROF_INFO_PERSON FOREIGN KEY (FK_ID_PERSON) REFERENCES REG_PERSON(ID),
    CONSTRAINT FK_MED_PROF_INFO_ORG    FOREIGN KEY (FK_ID_ORGANIZATION) REFERENCES REG_ORGANIZATION(ID),
    
    INDEX IDX_PROF_INFO_TENANT (TENANT_ID),
    INDEX IDX_PROF_INFO_PERSON (FK_ID_PERSON)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
