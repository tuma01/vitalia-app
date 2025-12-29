-- ============================================
-- Script: V1__create_tenant.sql
-- Tabla: TENANT
-- ============================================

CREATE TABLE IF NOT EXISTS TENANT (
    ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    CODE VARCHAR(100) NOT NULL,
    NAME VARCHAR(100) NOT NULL,
    TYPE ENUM('HOSPITAL','CLINIC','LABORATORY','PHARMACY', 'GLOBAL') NOT NULL,
    IS_ACTIVE BOOLEAN NOT NULL DEFAULT TRUE,
    DESCRIPTION TEXT,
    
    -- Theme Relation (Mapped directly here)
    THEME_ID BIGINT NULL,
    ADDRESS_ID BIGINT NULL,

    -- ===============================
    -- Auditoría
    -- ===============================
    CREATED_BY VARCHAR(100) NOT NULL,
    CREATED_DATE TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    LAST_MODIFIED_BY VARCHAR(100) NULL,
    LAST_MODIFIED_DATE TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,

    -- Constraints
    CONSTRAINT UK_TENANT_CODE  UNIQUE (CODE),
    CONSTRAINT FK_TENANT_THEME FOREIGN KEY (THEME_ID) REFERENCES THEME(ID)
    ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- Índices adicionales
-- ============================================
CREATE INDEX IDX_TENANT_NAME ON TENANT(NAME);
COMMIT;
