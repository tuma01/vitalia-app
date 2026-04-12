-- Script: V5_6__create_med_patient.sql
-- Módulo: vitalia-medical
-- Descripción: Creación de la tabla MED_PATIENT (SaaS Elite Tier - JOINED).
-- Autor: Juan Amachi
-- ============================================================

CREATE TABLE IF NOT EXISTS MED_PATIENT (
    -- ==========================================
    -- Identidad compartida con DMN_PERSON
    -- ==========================================
    ID                  BIGINT PRIMARY KEY,              -- PK vinculada a DMN_PERSON.ID
    TENANT_ID           VARCHAR(50) NOT NULL,            -- Denormalizado
    EXTERNAL_ID         VARCHAR(36) NOT NULL UNIQUE,
    VERSION             BIGINT DEFAULT 0 NOT NULL,
    IS_DELETED          BOOLEAN DEFAULT FALSE NOT NULL,

    -- ==========================================
    -- Habilitación Clínica
    -- ==========================================
    NHC                 VARCHAR(50) NOT NULL,            -- Local medical record number
    STATUS              VARCHAR(30) NOT NULL,            -- ACTIVE, INACTIVE, etc.
    PREFERRED_LANGUAGE  VARCHAR(20),
    REGISTRATION_DATE   DATETIME(6) NOT NULL,

    -- ==========================================
    -- Perfil y Demografía
    -- ==========================================
    OCCUPATION          VARCHAR(100),
    NATIONALITY         VARCHAR(100),
    EDUCATION_LEVEL     VARCHAR(100),
    ALLERGY_SUMMARY     VARCHAR(1000),
    IS_ACTIVE           BOOLEAN DEFAULT TRUE NOT NULL,
    ADDITIONAL_REMARKS  TEXT,

    -- ==========================================
    -- Biométricos (@Embedded PatientDetails)
    -- ==========================================
    BLOOD_GROUP         VARCHAR(30),
    WEIGHT              DECIMAL(5,2),
    HEIGHT              DECIMAL(5,2),
    HAS_DISABILITY      BOOLEAN DEFAULT FALSE NOT NULL,
    DISABILITY_DETAILS  VARCHAR(250),
    IS_PREGNANT         BOOLEAN DEFAULT FALSE NOT NULL,
    GESTATIONAL_WEEKS   INT,
    CHILDREN_COUNT      INT,
    ETHNIC_GROUP        VARCHAR(100),

    -- ==========================================
    -- Emergencia (@Embedded EmergencyContact)
    -- ==========================================
    EMERGENCY_NAME      VARCHAR(150),
    EMERGENCY_RELATION  VARCHAR(50),
    EMERGENCY_PHONE     VARCHAR(50),
    EMERGENCY_EMAIL     VARCHAR(100),
    EMERGENCY_ADDRESS   VARCHAR(250),

    -- ==========================================
    -- Relaciones Operativas
    -- ==========================================
    FK_ID_CURRENT_BED          BIGINT,
    FK_ID_CURRENT_HOSPITAL     BIGINT,
    PHOTO                      LONGBLOB,

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
    CONSTRAINT FK_MED_PAT_PERSON FOREIGN KEY (ID) REFERENCES DMN_PERSON(ID),
    CONSTRAINT FK_MED_PAT_BED    FOREIGN KEY (FK_ID_CURRENT_BED) REFERENCES MED_BED(ID),
    
    CONSTRAINT UK_MED_PAT_TENANT_NHC UNIQUE (TENANT_ID, NHC),
    
    INDEX IDX_PATIENT_TENANT (TENANT_ID),
    INDEX IDX_PATIENT_NHC    (NHC)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS MED_PATIENT_ALLERGY_MAP (
    ID_PATIENT  BIGINT NOT NULL,
    ID_ALLERGY  BIGINT NOT NULL,
    PRIMARY KEY (ID_PATIENT, ID_ALLERGY),
    CONSTRAINT FK_PAT_ALL_PAT FOREIGN KEY (ID_PATIENT) REFERENCES MED_PATIENT(ID),
    CONSTRAINT FK_PAT_ALL_MAP FOREIGN KEY (ID_ALLERGY) REFERENCES CAT_ALLERGY(ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
