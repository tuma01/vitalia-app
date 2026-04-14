-- ============================================================
-- Script: V5_8__create_med_nurse.sql
-- Módulo: vitalia-medical
-- Descripción: Creación de la tabla MED_NURSE (SaaS Elite - Composición).
-- ============================================================

CREATE TABLE IF NOT EXISTS MED_NURSE (
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
    -- Relaciones (Desacopladas)
    -- ==========================================
    FK_ID_USER          BIGINT,                          -- Vínculo a cuenta de usuario
    FK_ID_DEPT_UNIT     BIGINT,                          -- Unidad de adscripción
    FK_ID_USERPROFILE   BIGINT,                          -- Perfil extendido
    -- FK_ID_EMPLOYEE eliminada (se vincula vía person_id)

    -- ==========================================
    -- Datos Profesionales
    -- ==========================================
    NURSE_LICENSE       VARCHAR(100) NOT NULL,
    LICENSE_EXPIRY      DATE,
    HIRE_DATE           DATE,
    CONTRACT_TYPE       VARCHAR(50),
    NURSE_EMERGENCY_CONTACT VARCHAR(200),
    PHOTO               LONGBLOB,
    
    NURSE_RANK          VARCHAR(100),                    -- Categoría (LICENCIADA, JEFE)
    NURSE_SHIFT         VARCHAR(50),                     -- Turno (MORNING, AFTERNOON)
    IS_ACTIVE           TINYINT(1) DEFAULT 1 NOT NULL,

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
    CONSTRAINT FK_MED_NURSE_PERSON FOREIGN KEY (FK_ID_PERSON) REFERENCES DMN_PERSON(ID),
    CONSTRAINT FK_MED_NURSE_UNIT   FOREIGN KEY (FK_ID_DEPT_UNIT) REFERENCES MED_DEPARTMENT_UNIT(ID),
    
    -- Unicidad Elite: Una identidad, una enfermera, un hospital
    CONSTRAINT UK_MED_NURSE_IDENTITY_TENANT UNIQUE (FK_ID_PERSON, TENANT_ID, IS_DELETED),
    CONSTRAINT UK_MED_NURSE_TENANT_LICENSE  UNIQUE (TENANT_ID, NURSE_LICENSE, IS_DELETED),
    
    INDEX IDX_NURSE_TENANT (TENANT_ID),
    INDEX IDX_NURSE_LICENSE (NURSE_LICENSE),
    INDEX IDX_NURSE_PERSON (FK_ID_PERSON)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tablas de colección mapeadas
CREATE TABLE IF NOT EXISTS MED_NURSE_SKILLS (
    ID_NURSE BIGINT NOT NULL,
    SKILL    VARCHAR(255) NOT NULL,
    CONSTRAINT FK_NURSE_SKILL_ID FOREIGN KEY (ID_NURSE) REFERENCES MED_NURSE(ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS MED_NURSE_SPECIALITY_MAP (
    ID_NURSE      BIGINT NOT NULL,
    ID_SPECIALITY BIGINT NOT NULL,
    PRIMARY KEY (ID_NURSE, ID_SPECIALITY),
    CONSTRAINT FK_NURSE_SPEC_ID FOREIGN KEY (ID_NURSE) REFERENCES MED_NURSE(ID),
    CONSTRAINT FK_NURSE_SPEC_CAT FOREIGN KEY (ID_SPECIALITY) REFERENCES CAT_MEDICAL_SPECIALTY(ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
