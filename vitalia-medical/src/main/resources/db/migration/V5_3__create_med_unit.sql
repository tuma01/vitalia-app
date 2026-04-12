-- Script: V5_3__create_med_unit.sql
-- Módulo: vitalia-medical
-- Descripción: Creación de la tabla MED_DEPARTMENT_UNIT (SaaS Elite Tier).
-- Autor: Juan Amachi
-- ============================================================

CREATE TABLE IF NOT EXISTS MED_DEPARTMENT_UNIT (
    -- ==========================================
    -- Identidad y Auditoría Base
    -- ==========================================
    ID                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    TENANT_ID           VARCHAR(50) NOT NULL,
    EXTERNAL_ID         VARCHAR(36) NOT NULL UNIQUE,
    VERSION             BIGINT DEFAULT 0 NOT NULL,
    IS_DELETED          BOOLEAN DEFAULT FALSE NOT NULL,

    -- ==========================================
    -- Datos de la Unidad
    -- ==========================================
    FK_ID_UNIT_TYPE     BIGINT NOT NULL,
    FK_ID_EMPLOYEE      BIGINT,                          -- Jefe de unidad
    FK_PARENT_UNIT      BIGINT,                          -- Jerarquía (Padre)
    CODE                VARCHAR(50) NOT NULL,
    NAME                VARCHAR(150) NOT NULL,
    FLOOR               VARCHAR(50),
    CONTACT_PHONE       VARCHAR(50),
    DESCRIPTION         VARCHAR(500),
    MAX_CAPACITY        INT,
    IS_CLINICAL         BOOLEAN DEFAULT TRUE,
    COST_CENTER         VARCHAR(50),
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
    CONSTRAINT FK_MED_UNIT_TYPE    FOREIGN KEY (FK_ID_UNIT_TYPE) REFERENCES MED_DEPARTMENT_UNIT_TYPE(ID),
    CONSTRAINT FK_MED_UNIT_HEAD    FOREIGN KEY (FK_ID_EMPLOYEE)  REFERENCES MED_EMPLOYEE(ID),
    CONSTRAINT FK_MED_UNIT_PARENT  FOREIGN KEY (FK_PARENT_UNIT)  REFERENCES MED_DEPARTMENT_UNIT(ID),

    INDEX IDX_UNIT_TENANT (TENANT_ID),
    INDEX IDX_UNIT_CODE   (CODE),
    CONSTRAINT UK_UNIT_TENANT_CODE UNIQUE (TENANT_ID, CODE),
    CONSTRAINT UK_UNIT_TENANT_NAME UNIQUE (TENANT_ID, NAME)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
