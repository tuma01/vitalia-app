-- Script: V5_8__create_med_nurse.sql
-- Módulo: vitalia-medical
-- Descripción: Creación de la tabla MED_NURSE (SaaS Elite Tier - JOINED).
-- Autor: Juan Amachi
-- ============================================================

CREATE TABLE IF NOT EXISTS MED_NURSE (
    -- ==========================================
    -- Identidad y Auditoría Base
    -- ==========================================
    ID                  BIGINT PRIMARY KEY,              -- Inherited from DMN_PERSON
    TENANT_ID           VARCHAR(50) NOT NULL,
    EXTERNAL_ID         VARCHAR(36) NOT NULL UNIQUE,
    VERSION             BIGINT DEFAULT 0 NOT NULL,
    IS_DELETED          BOOLEAN DEFAULT FALSE NOT NULL,

    -- ==========================================
    -- Relaciones
    -- ==========================================
    FK_ID_USER          BIGINT,                          -- Vínculo a cuenta de usuario
    FK_ID_DEPT_UNIT     BIGINT,                          -- Unidad de adscripción
    FK_ID_USERPROFILE   BIGINT,                          -- Perfil extendido
    FK_ID_EMPLOYEE      BIGINT,                          -- Registro de RRHH

    -- ==========================================
    -- Datos Profesionales
    -- ==========================================
    NURSE_LICENSE       VARCHAR(100) NOT NULL,
    LICENSE_EXPIRY      DATE,
    HIRE_DATE           DATE,
    ID_CARD             VARCHAR(100),                    -- Cédula de Identidad STAFF
    CONTRACT_TYPE       VARCHAR(50),
    NURSE_EMERGENCY_CONTACT VARCHAR(200),
    PHOTO               LONGBLOB,
    
    NURSE_RANK          VARCHAR(100),                    -- Categoría (LICENCIADA, JEFE)
    NURSE_SHIFT         VARCHAR(50),                     -- Turno (MORNING, AFTERNOON)
    IS_ACTIVE           BOOLEAN DEFAULT TRUE,

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
    CONSTRAINT FK_MED_NURSE_PERSON FOREIGN KEY (ID) REFERENCES DMN_PERSON(ID),
    CONSTRAINT FK_MED_NURSE_UNIT   FOREIGN KEY (FK_ID_DEPT_UNIT) REFERENCES MED_DEPARTMENT_UNIT(ID),
    
    INDEX IDX_NURSE_TENANT (TENANT_ID),
    INDEX IDX_NURSE_LICENSE (NURSE_LICENSE),
    
    CONSTRAINT UK_NURSE_TENANT_LICENSE UNIQUE (TENANT_ID, NURSE_LICENSE)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
