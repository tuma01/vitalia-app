-- ============================================================
-- Script: V5_3__create_med_unit.sql
-- Módulo: vitalia-medical
-- Descripción: Creación de la tabla MED_DEPARTMENT_UNIT (SaaS Elite Tier).
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
    -- Jerarquía y Relaciones
    -- ==========================================
    FK_ID_HOSPITAL      BIGINT NOT NULL,
    FK_ID_UNIT_TYPE     BIGINT NOT NULL,
    FK_PARENT_UNIT      BIGINT,

    -- ==========================================
    -- Datos de la Unidad
    -- ==========================================
    NAME                VARCHAR(100) NOT NULL,
    CODE                VARCHAR(50) NOT NULL,
    FLOOR               INT,
    IS_ACTIVE           BOOLEAN DEFAULT TRUE,

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
    CONSTRAINT FK_UNIT_HOSPITAL FOREIGN KEY (FK_ID_HOSPITAL) REFERENCES MGT_HOSPITAL(ID),
    CONSTRAINT FK_UNIT_TYPE     FOREIGN KEY (FK_ID_UNIT_TYPE) REFERENCES CAT_MEDICAL_UNIT_TYPE(ID),
    CONSTRAINT FK_UNIT_PARENT   FOREIGN KEY (FK_PARENT_UNIT) REFERENCES MED_DEPARTMENT_UNIT(ID),

    INDEX IDX_UNIT_TENANT (TENANT_ID),
    CONSTRAINT UK_UNIT_TENANT_CODE UNIQUE (TENANT_ID, CODE)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
