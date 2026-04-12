-- Script: V5_37__create_med_doctor_hospital_assign.sql
-- Módulo: vitalia-medical
-- Descripción: Creación de la tabla MED_DOCTOR_HOSPITAL_ASSIGN (SaaS Elite Tier).
-- Autor: Juan Amachi
-- ============================================================

CREATE TABLE IF NOT EXISTS MED_DOCTOR_HOSPITAL_ASSIGN (
    -- ==========================================
    -- Identidad y Auditoría Base
    -- ==========================================
    ID                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    TENANT_ID           VARCHAR(50) NOT NULL,
    EXTERNAL_ID         VARCHAR(36) NOT NULL UNIQUE,
    VERSION             BIGINT DEFAULT 0 NOT NULL,
    IS_DELETED          BOOLEAN DEFAULT FALSE NOT NULL,

    -- ==========================================
    -- Relaciones
    -- ==========================================
    FK_ID_DOCTOR        BIGINT NOT NULL,
    FK_ID_HOSPITAL      BIGINT NOT NULL,

    -- ==========================================
    -- Datos de Asignación / Itinerancia
    -- ==========================================
    START_DATE          DATE NOT NULL,
    END_DATE            DATE,
    IS_PRIMARY          BOOLEAN DEFAULT FALSE NOT NULL,

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
    CONSTRAINT FK_MED_DHA_DOCTOR   FOREIGN KEY (FK_ID_DOCTOR) REFERENCES MED_DOCTOR(ID),
    CONSTRAINT FK_MED_DHA_HOSPITAL FOREIGN KEY (FK_ID_HOSPITAL) REFERENCES REG_HOSPITAL(ID),
    
    INDEX IDX_DHA_TENANT (TENANT_ID),
    INDEX IDX_DHA_DOCTOR (FK_ID_DOCTOR)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
