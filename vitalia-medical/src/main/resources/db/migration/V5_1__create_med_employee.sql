-- Script: V5_1__create_med_employee.sql
-- Módulo: vitalia-medical
-- Descripción: Creación de la tabla MED_EMPLOYEE (SaaS Elite Tier - JOINED).
-- Autor: Juan Amachi
-- ============================================================

CREATE TABLE IF NOT EXISTS MED_EMPLOYEE (
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
    FK_ID_DEPT_UNIT     BIGINT,                          -- Departamento de adscripción

    -- ==========================================
    -- Datos Administrativos
    -- ==========================================
    EMPLOYEE_CODE       VARCHAR(50) NOT NULL,            -- Código de RRHH unique internal
    EMPLOYEE_TYPE       VARCHAR(40) NOT NULL,            -- DOCTOR, NURSE, ADMIN, etc.
    EMPLOYEE_STATUS     VARCHAR(30) NOT NULL,            -- ACTIVE, ON_LEAVE, TERMINATED
    JOB_POSITION        VARCHAR(120),
    HIRE_DATE           DATE,
    SALARY              DECIMAL(12,2),
    EMPLOYMENT_TYPE     VARCHAR(50),                     -- FULL_TIME, PART_TIME
    WORK_SHIFT          VARCHAR(50),
    EMP_EMERGENCY_CONTACT VARCHAR(200),
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
    CONSTRAINT FK_MED_EMP_PERSON FOREIGN KEY (ID) REFERENCES DMN_PERSON(ID),
    CONSTRAINT FK_MED_EMP_USER   FOREIGN KEY (FK_ID_USER) REFERENCES AUT_USER(ID),
    CONSTRAINT FK_MED_EMP_UNIT   FOREIGN KEY (FK_ID_DEPT_UNIT) REFERENCES MED_DEPARTMENT_UNIT(ID),

    INDEX IDX_EMP_TENANT (TENANT_ID),
    INDEX IDX_EMP_CODE   (EMPLOYEE_CODE),
    CONSTRAINT UK_EMP_TENANT_CODE UNIQUE (TENANT_ID, EMPLOYEE_CODE)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
