-- Script: V5_7__create_med_doctor.sql
-- Módulo: vitalia-medical
-- Descripción: Creación de la tabla MED_DOCTOR (SaaS Elite Tier - JOINED).
-- Autor: Juan Amachi
-- ============================================================

CREATE TABLE IF NOT EXISTS MED_DOCTOR (
    -- ==========================================
    -- Identidad compartida con DMN_PERSON
    -- ==========================================
    ID                  BIGINT PRIMARY KEY,              -- PK vinculada a DMN_PERSON.ID
    TENANT_ID           VARCHAR(50) NOT NULL,            -- Denormalizado para integridad de constraints
    EXTERNAL_ID         VARCHAR(36) NOT NULL UNIQUE,     -- UUID clínico
    VERSION             BIGINT DEFAULT 0 NOT NULL,
    IS_DELETED          BOOLEAN DEFAULT FALSE NOT NULL,

    -- ==========================================
    -- Vinculación Profesional
    -- ==========================================
    FK_ID_USER          BIGINT,
    FK_ID_DEPT_UNIT     BIGINT,
    FK_ID_USERPROFILE   BIGINT,
    FK_ID_EMPLOYEE      BIGINT,

    -- ==========================================
    -- Credenciales
    -- ==========================================
    LICENSE_NUMBER      VARCHAR(100) NOT NULL,
    LICENSE_EXPIRY      DATE,
    HIRE_DATE           DATE,
    RAMQ_PROVIDER_NUMBER VARCHAR(50),
    SIGNATURE_DIGITAL_PATH VARCHAR(255),

    -- ==========================================
    -- Operativo y Disponibilidad
    -- ==========================================
    OFFICE_NUMBER       VARCHAR(50),
    CONSULTATION_PRICE  DECIMAL(12,2),
    AVAILABILITY_STATUS VARCHAR(50),
    IS_AVAILABLE        BOOLEAN DEFAULT TRUE NOT NULL,
    IS_ACTIVE           BOOLEAN DEFAULT TRUE NOT NULL,

    -- ==========================================
    -- Perfil Clínico y Métricas
    -- ==========================================
    YEARS_OF_EXPERIENCE INT,
    RATING              DECIMAL(3,1),
    TOTAL_CONSULTATIONS INT DEFAULT 0,
    SPECIALTIES_SUMMARY VARCHAR(500),
    BIO                 TEXT,

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
    CONSTRAINT FK_MED_DOC_PERSON      FOREIGN KEY (ID) REFERENCES DMN_PERSON(ID),
    CONSTRAINT FK_MED_DOC_USER        FOREIGN KEY (FK_ID_USER) REFERENCES AUT_USER(ID),
    CONSTRAINT FK_MED_DOC_UNIT        FOREIGN KEY (FK_ID_DEPT_UNIT) REFERENCES MED_DEPARTMENT_UNIT(ID),
    CONSTRAINT FK_MED_DOC_PROFILE     FOREIGN KEY (FK_ID_USERPROFILE) REFERENCES MED_USER_PROFILE(ID),
    CONSTRAINT FK_MED_DOC_HR          FOREIGN KEY (FK_ID_EMPLOYEE) REFERENCES MED_EMPLOYEE(ID),
    
    CONSTRAINT UK_MED_DOC_TENANT_LICENSE UNIQUE (TENANT_ID, LICENSE_NUMBER),
    
    INDEX IDX_DOCTOR_TENANT  (TENANT_ID),
    INDEX IDX_DOCTOR_LICENSE (LICENSE_NUMBER)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tablas de colección mapeadas
CREATE TABLE IF NOT EXISTS MED_DOCTOR_PROCEDURES (
    ID_DOCTOR      BIGINT NOT NULL,
    PROCEDURE_NAME VARCHAR(255) NOT NULL,
    CONSTRAINT FK_DOC_PROC_DR FOREIGN KEY (ID_DOCTOR) REFERENCES MED_DOCTOR(ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS MED_DOCTOR_SPECIALITY_MAP (
    ID_DOCTOR     BIGINT NOT NULL,
    ID_SPECIALITY BIGINT NOT NULL,
    PRIMARY KEY (ID_DOCTOR, ID_SPECIALITY),
    CONSTRAINT FK_DOC_SPEC_DR   FOREIGN KEY (ID_DOCTOR) REFERENCES MED_DOCTOR(ID),
    CONSTRAINT FK_DOC_SPEC_CAT  FOREIGN KEY (ID_SPECIALITY) REFERENCES CAT_MEDICAL_SPECIALTY(ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
