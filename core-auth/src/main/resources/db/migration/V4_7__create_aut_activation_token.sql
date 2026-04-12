-- ============================================================
-- V4_7__create_aut_activation_token.sql
-- Tabla para tokens de activación de cuenta (onboarding médico).
-- Posee aislamiento nativo multi-tenant (Elite Tier).
-- ============================================================

CREATE TABLE IF NOT EXISTS AUT_ACTIVATION_TOKEN (
    ID                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    TENANT_ID           VARCHAR(50)     NOT NULL,
    EXTERNAL_ID         VARCHAR(36)     UNIQUE,
    VERSION             BIGINT          DEFAULT 0 NOT NULL,
    IS_DELETED          TINYINT(1)      DEFAULT 0 NOT NULL,

    TOKEN               VARCHAR(100)    NOT NULL,
    FK_ID_USER          BIGINT          NOT NULL,

    EXPIRES_AT          DATETIME        NOT NULL,
    USED                TINYINT(1)      DEFAULT 0 NOT NULL,

    -- Auditoría (heredada de Auditable)
    CREATED_BY          VARCHAR(100)    NOT NULL,
    CREATED_DATE        TIMESTAMP       NOT NULL,
    LAST_MODIFIED_BY    VARCHAR(100),
    LAST_MODIFIED_DATE  TIMESTAMP,

    -- Constraints
    CONSTRAINT UK_ACTIVATION_TOKEN      UNIQUE (TOKEN),
    
    CONSTRAINT FK_ACTIVATION_USER
        FOREIGN KEY (FK_ID_USER) REFERENCES AUT_USER (ID)
        ON DELETE CASCADE,

    CONSTRAINT FK_ACTIVATION_TENANT
        FOREIGN KEY (TENANT_ID) REFERENCES DMN_TENANT (CODE)
        ON DELETE CASCADE
        ON UPDATE CASCADE

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Índices para mejorar el rendimiento de las consultas
CREATE INDEX IDX_ACTIVATION_TOKEN_USER    ON AUT_ACTIVATION_TOKEN (FK_ID_USER);
CREATE INDEX IDX_ACTIVATION_TOKEN_TENANT  ON AUT_ACTIVATION_TOKEN (TENANT_ID);
CREATE INDEX IDX_ACTIVATION_TOKEN_TOKEN   ON AUT_ACTIVATION_TOKEN (TOKEN);
