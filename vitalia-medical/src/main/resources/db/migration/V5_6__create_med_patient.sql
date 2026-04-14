-- ============================================================
-- Script: V5_6__create_med_patient.sql
-- Módulo: vitalia-medical
-- Descripción: Creación de la tabla MED_PATIENT (SaaS Elite - Composición).
--              Desacoplamiento de Identidad Global y Rol Multi-tenant.
-- ============================================================

CREATE TABLE IF NOT EXISTS MED_PATIENT (
    -- ==========================================
    -- Infraestructura de Aislamiento (BaseTenantEntity)
    -- ==========================================
    ID                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    TENANT_ID           VARCHAR(50) NOT NULL,
    EXTERNAL_ID         VARCHAR(36) NOT NULL UNIQUE,
    VERSION             BIGINT DEFAULT 0 NOT NULL,
    IS_DELETED          TINYINT(1) DEFAULT 0 NOT NULL,

    -- ==========================================
    -- Composición de Identidad (Person Mapping)
    -- ==========================================
    FK_ID_PERSON        BIGINT NOT NULL,                 -- Vínculo a la Identidad Global

    -- ==========================================
    -- Habilitación Clínica Local
    -- ==========================================
    NHC                 VARCHAR(50) NOT NULL,            -- Local medical record number
    STATUS              VARCHAR(30) NOT NULL,            -- ACTIVE, INACTIVE, etc.
    PREFERRED_LANGUAGE  VARCHAR(20),
    REGISTRATION_DATE   DATETIME(6) NOT NULL,

    -- ==========================================
    -- Perfil y Demografía Local
    -- ==========================================
    OCCUPATION          VARCHAR(100),
    NATIONALITY         VARCHAR(100),
    EDUCATION_LEVEL     VARCHAR(100),
    ALLERGY_SUMMARY     VARCHAR(1000),
    IS_ACTIVE           TINYINT(1) DEFAULT 1 NOT NULL,
    ADDITIONAL_REMARKS  TEXT,

    -- ==========================================
    -- Biometrics (@Embedded PatientDetails)
    -- ==========================================
    FK_ID_BLOOD_TYPE    BIGINT,                          -- Refers to CAT_BLOOD_TYPE
    WEIGHT              DECIMAL(5,2),
    HEIGHT              DECIMAL(5,2),
    HAS_DISABILITY      TINYINT(1) DEFAULT 0 NOT NULL,
    DISABILITY_DETAILS  VARCHAR(250),
    IS_PREGNANT         TINYINT(1) DEFAULT 0 NOT NULL,
    GESTATIONAL_WEEKS   INT,
    CHILDREN_COUNT      INT,
    ETHNIC_GROUP        VARCHAR(100),

    -- ==========================================
    -- Emergency (@Embedded EmergencyContact)
    -- ==========================================
    EMERGENCY_NAME      VARCHAR(150),
    EMERGENCY_RELATION  VARCHAR(50),
    EMERGENCY_PHONE     VARCHAR(50),
    EMERGENCY_EMAIL     VARCHAR(100),
    EMERGENCY_ADDRESS   VARCHAR(250),

    -- ==========================================
    -- Operational Relationships
    -- ==========================================
    FK_ID_CURRENT_BED          BIGINT,
    FK_ID_CURRENT_HOSPITAL     BIGINT,
    PHOTO                      LONGBLOB,

    -- ==========================================
    -- Operational Audit
    -- ==========================================
    CREATED_BY          VARCHAR(100) NOT NULL,
    CREATED_DATE        DATETIME(6)  NOT NULL,
    LAST_MODIFIED_BY    VARCHAR(100),
    LAST_MODIFIED_DATE  DATETIME(6),

    -- ==========================================
    -- Constraints & Indexes
    -- ==========================================
    CONSTRAINT FK_MED_PAT_PERSON FOREIGN KEY (FK_ID_PERSON) REFERENCES DMN_PERSON(ID),
    CONSTRAINT FK_MED_PAT_BED    FOREIGN KEY (FK_ID_CURRENT_BED) REFERENCES MED_BED(ID),
    CONSTRAINT FK_MED_PAT_BLOOD  FOREIGN KEY (FK_ID_BLOOD_TYPE) REFERENCES CAT_BLOOD_TYPE(ID),
    
    -- El corazón de la arquitectura: Una identidad, un paciente, un hospital.
    CONSTRAINT UK_MED_PAT_IDENTITY_TENANT UNIQUE (FK_ID_PERSON, TENANT_ID, IS_DELETED),
    CONSTRAINT UK_MED_PAT_TENANT_NHC      UNIQUE (TENANT_ID, NHC, IS_DELETED),
    
    INDEX IDX_PATIENT_TENANT (TENANT_ID),
    INDEX IDX_PATIENT_NHC    (NHC),
    INDEX IDX_PATIENT_PERSON (FK_ID_PERSON)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS MED_PATIENT_ALLERGY_MAP (
    ID_PATIENT  BIGINT NOT NULL,
    ID_ALLERGY  BIGINT NOT NULL,
    PRIMARY KEY (ID_PATIENT, ID_ALLERGY),
    CONSTRAINT FK_PAT_ALL_PAT FOREIGN KEY (ID_PATIENT) REFERENCES MED_PATIENT(ID),
    CONSTRAINT FK_PAT_ALL_MAP FOREIGN KEY (ID_ALLERGY) REFERENCES CAT_ALLERGY(ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
