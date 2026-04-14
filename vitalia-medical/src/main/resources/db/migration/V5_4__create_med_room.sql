-- ============================================================
-- Script: V5_4__create_med_room.sql
-- Módulo: vitalia-medical
-- Descripción: Creación de la tabla MED_ROOM (SaaS Elite Tier).
-- ============================================================

CREATE TABLE IF NOT EXISTS MED_ROOM (
    -- ==========================================
    -- Identidad y Auditoría Base
    -- ==========================================
    ID                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    TENANT_ID           VARCHAR(50) NOT NULL,
    EXTERNAL_ID         VARCHAR(36) NOT NULL UNIQUE,
    VERSION             BIGINT DEFAULT 0 NOT NULL,
    IS_DELETED          BOOLEAN DEFAULT FALSE NOT NULL,

    -- ==========================================
    -- Jerarquía
    -- ==========================================
    FK_ID_DEPT_UNIT     BIGINT NOT NULL,

    -- ==========================================
    -- Datos de la Habitación
    -- ==========================================
    ROOM_NUMBER         VARCHAR(50) NOT NULL,
    BLOCK_CODE          VARCHAR(50),
    BLOCK_FLOOR         INT,
    IS_PRIVATE          BOOLEAN DEFAULT FALSE NOT NULL,
    ROOM_TYPE           VARCHAR(50) NOT NULL,            -- STANDARD, PRIVATE, SUITE, ICU, etc.
    CLEANING_STATUS     VARCHAR(30) DEFAULT 'CLEAN',     -- CLEAN, DIRTY, IN_PROGRESS
    DESCRIPTION         VARCHAR(500),
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
    CONSTRAINT FK_MED_ROOM_UNIT FOREIGN KEY (FK_ID_DEPT_UNIT) REFERENCES MED_DEPARTMENT_UNIT(ID),

    INDEX IDX_ROOM_TENANT (TENANT_ID),
    INDEX IDX_ROOM_UNIT   (FK_ID_DEPT_UNIT),
    CONSTRAINT UK_ROOM_TENANT_UNIT_NUMBER UNIQUE (TENANT_ID, FK_ID_DEPT_UNIT, ROOM_NUMBER)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
