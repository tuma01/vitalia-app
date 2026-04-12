-- Script: V5_12__create_med_appointment_reminder.sql
-- Módulo: vitalia-medical
-- Descripción: Creación de la tabla MED_APPOINTMENT_REMINDER (SaaS Elite Tier).
-- Autor: Juan Amachi
-- ============================================================

CREATE TABLE IF NOT EXISTS MED_APPOINTMENT_REMINDER (
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
    FK_ID_APPOINTMENT   BIGINT NOT NULL,

    -- ==========================================
    -- Datos del Recordatorio
    -- ==========================================
    CHANNEL             VARCHAR(30) NOT NULL,            -- EMAIL, SMS, WHATSAPP, PUSH
    REMINDER_STATUS     VARCHAR(50) NOT NULL,
    SCHEDULED_DATE      DATETIME(6) NOT NULL,
    SENT_DATE           DATETIME(6),
    READ_DATE           DATETIME(6),
    RETRY_COUNT         INT DEFAULT 0,
    TARGET              VARCHAR(150),                    -- Destination address/number
    EXTERNAL_MESSAGE_ID VARCHAR(255),                    -- ID from gateway (SendGrid, Twilio)
    GATEWAY_RESPONSE    TEXT,

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
    CONSTRAINT FK_MED_REM_APP FOREIGN KEY (FK_ID_APPOINTMENT) REFERENCES MED_APPOINTMENT(ID),
    
    INDEX IDX_REM_TENANT  (TENANT_ID),
    INDEX IDX_REM_APP     (FK_ID_APPOINTMENT)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
