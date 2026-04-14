-- ============================================================
-- Script: V5_10__create_med_hospitalization.sql
-- Módulo: vitalia-medical
-- Descripción: Creación de la tabla MED_HOSPITALIZATION (SaaS Elite Tier).
-- ============================================================

CREATE TABLE IF NOT EXISTS MED_HOSPITALIZATION (
    -- ==========================================
    -- Identidad y Auditoría Base
    -- ==========================================
    ID                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    TENANT_ID           VARCHAR(50) NOT NULL,
    EXTERNAL_ID         VARCHAR(36) NOT NULL UNIQUE,
    VERSION             BIGINT DEFAULT 0 NOT NULL,
    IS_DELETED          BOOLEAN DEFAULT FALSE NOT NULL,

    -- ==========================================
    -- Relaciones (FKs)
    -- ==========================================
    FK_ID_PATIENT       BIGINT NOT NULL,
    FK_ID_ENCOUNTER     BIGINT,                          -- Vínculo con el encuentro clínico origen
    FK_ID_UNIT          BIGINT,                          -- Unidad de hospitalización
    FK_ID_ROOM          BIGINT,
    FK_ID_BED           BIGINT,
    FK_ID_DR_RESP       BIGINT,                          -- Médico responsable
    FK_ID_NS_RESP       BIGINT,                          -- Enfermero/a responsable

    -- ==========================================
    -- Control Clínico y Operativo
    -- ==========================================
    ADMISSION_DATE      DATETIME(6) NOT NULL,
    DISCHARGE_DATE      DATETIME(6),
    ADMISSION_TYPE      VARCHAR(50) NOT NULL,            -- URGENCY, PROGRAMMED
    HOSPITALIZATION_STATUS VARCHAR(50) NOT NULL,         -- ADMITTED, DISCHARGED
    HOSPITALIZATION_PRIORITY VARCHAR(30),                -- NORMAL, CRITICAL
    DISCHARGE_STATUS    VARCHAR(30),                     -- RECOVERED, DECEASED, etc.
    
    ADMISSION_REASON    VARCHAR(1000),
    ADMISSION_DIAGNOSIS VARCHAR(500),
    FINAL_DIAGNOSIS     VARCHAR(500),
    TREATMENT_PLAN      TEXT,
    CLINICAL_NOTES      TEXT,
    DISCHARGE_REASON    VARCHAR(1000),
    DISCHARGE_INSTRUCTIONS TEXT,
    
    ESTIMATED_DISCHARGE_DATE DATETIME(6),
    FOLLOW_UP_DATE      DATE,
    
    INSURANCE_AUTH_NUMBER VARCHAR(100),
    CURRENCY            VARCHAR(10) DEFAULT 'USD',
    TOTAL_COST          DECIMAL(12,2),
    OBSERVATIONS        TEXT,

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
    CONSTRAINT FK_MED_HOSP_PATIENT FOREIGN KEY (FK_ID_PATIENT) REFERENCES MED_PATIENT(ID),
    CONSTRAINT FK_MED_HOSP_ENC     FOREIGN KEY (FK_ID_ENCOUNTER) REFERENCES MED_ENCOUNTER(ID),
    CONSTRAINT FK_MED_HOSP_UNIT    FOREIGN KEY (FK_ID_UNIT) REFERENCES MED_DEPARTMENT_UNIT(ID),
    CONSTRAINT FK_MED_HOSP_ROOM    FOREIGN KEY (FK_ID_ROOM) REFERENCES MED_ROOM(ID),
    CONSTRAINT FK_MED_HOSP_BED     FOREIGN KEY (FK_ID_BED) REFERENCES MED_BED(ID),
    CONSTRAINT FK_MED_HOSP_DR      FOREIGN KEY (FK_ID_DR_RESP) REFERENCES MED_DOCTOR(ID),
    CONSTRAINT FK_MED_HOSP_NS      FOREIGN KEY (FK_ID_NS_RESP) REFERENCES MED_NURSE(ID),

    INDEX IDX_HOSP_TENANT  (TENANT_ID),
    INDEX IDX_HOSP_PATIENT (FK_ID_PATIENT),
    INDEX IDX_HOSP_STATUS  (HOSPITALIZATION_STATUS),
    INDEX IDX_HOSP_DATE    (ADMISSION_DATE)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
