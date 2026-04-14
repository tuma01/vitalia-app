-- ============================================================
-- Script: V5_18__create_med_insurance.sql
-- Módulo: vitalia-medical
-- Descripción: Creación de la tabla MED_INSURANCE (SaaS Elite Tier).
-- ============================================================

CREATE TABLE IF NOT EXISTS MED_INSURANCE (
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
    FK_ID_MEDICAL_HISTORY    BIGINT NOT NULL,
    FK_ID_HEALTHCARE_PROVIDER BIGINT NOT NULL,

    -- ==========================================
    -- Datos de la Póliza
    -- ==========================================
    POLICY_NUMBER       VARCHAR(50) NOT NULL,
    POLICY_TYPE         VARCHAR(50),                     -- COMPREHENSIVE, BASIC, EMERGENCY_ONLY
    EFFECTIVE_DATE      DATE,
    EXPIRATION_DATE     DATE,
    COVERAGE_DETAILS    TEXT,
    COPAY_AMOUNT        DECIMAL(12,2),
    DEDUCTIBLE_AMOUNT   DECIMAL(12,2),
    AUTH_REQUIRED       BOOLEAN DEFAULT FALSE,           -- Requires prior authorization
    IS_CURRENT          BOOLEAN DEFAULT TRUE NOT NULL,

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
    CONSTRAINT FK_MED_INS_HISTORY  FOREIGN KEY (FK_ID_MEDICAL_HISTORY) REFERENCES MED_MEDICAL_HISTORY(ID),
    CONSTRAINT FK_MED_INS_PROVIDER FOREIGN KEY (FK_ID_HEALTHCARE_PROVIDER) REFERENCES MED_HEALTHCARE_PROVIDER(ID),

    INDEX IDX_INS_TENANT  (TENANT_ID),
    INDEX IDX_INS_HISTORY (FK_ID_MEDICAL_HISTORY),
    INDEX IDX_INS_NUMBER  (POLICY_NUMBER),
    
    CONSTRAINT UK_INS_TENANT_POLICY UNIQUE (TENANT_ID, POLICY_NUMBER)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
