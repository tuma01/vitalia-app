-- ============================================================
-- Script: V5_7__create_med_doctor.sql
-- Módulo: vitalia-medical
-- Descripción: Creación de la tabla MED_DOCTOR (SaaS Elite - Composición).
-- ============================================================

CREATE TABLE IF NOT EXISTS MED_DOCTOR (
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
    -- Vinculación Profesional (Desacoplada)
    -- ==========================================
    FK_ID_USER          BIGINT,
    FK_ID_DEPT_UNIT     BIGINT,
    FK_ID_USERPROFILE   BIGINT,
    -- FK_ID_EMPLOYEE eliminada (se vincula vía person_id)

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
    IS_AVAILABLE        TINYINT(1) DEFAULT 1 NOT NULL,
    IS_ACTIVE           TINYINT(1) DEFAULT 1 NOT NULL,

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
    CREATED_DATE        DATETIME(6)  NOT NULL,
    LAST_MODIFIED_BY    VARCHAR(100),
    LAST_MODIFIED_DATE  DATETIME(6),

    -- ==========================================
    -- Constraints & Indexes
    -- ==========================================
    CONSTRAINT FK_MED_DOC_PERSON      FOREIGN KEY (FK_ID_PERSON) REFERENCES DMN_PERSON(ID),
    CONSTRAINT FK_MED_DOC_USER        FOREIGN KEY (FK_ID_USER) REFERENCES AUT_USER(ID),
    CONSTRAINT FK_MED_DOC_UNIT        FOREIGN KEY (FK_ID_DEPT_UNIT) REFERENCES MED_DEPARTMENT_UNIT(ID),
    CONSTRAINT FK_MED_DOC_PROFILE     FOREIGN KEY (FK_ID_USERPROFILE) REFERENCES MED_USER_PROFILE(ID),
    
    -- Unicidad Elite: Un profesional por identidad por hospital
    CONSTRAINT UK_MED_DOC_IDENTITY_TENANT UNIQUE (FK_ID_PERSON, TENANT_ID, IS_DELETED),
    CONSTRAINT UK_MED_DOC_TENANT_LICENSE  UNIQUE (TENANT_ID, LICENSE_NUMBER, IS_DELETED),
    
    INDEX IDX_DOCTOR_TENANT  (TENANT_ID),
    INDEX IDX_DOCTOR_LICENSE (LICENSE_NUMBER),
    INDEX IDX_DOCTOR_PERSON  (FK_ID_PERSON)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

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
