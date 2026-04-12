-- ============================================================
-- V4_11__create_aut_blacklisted_token.sql
-- Tabla para tokens invalidados (logout/blacklist).
-- Posee aislamiento nativo multi-tenant (Elite Tier).
-- ============================================================

CREATE TABLE IF NOT EXISTS AUT_BLACKLISTED_TOKEN (
    ID                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    TENANT_ID           VARCHAR(50)     NOT NULL,
    EXTERNAL_ID         VARCHAR(36)     UNIQUE,
    VERSION             BIGINT          DEFAULT 0 NOT NULL,
    IS_DELETED          TINYINT(1)      DEFAULT 0 NOT NULL,

    TOKEN               VARCHAR(500)    NOT NULL,

    BLACKLISTED_AT      DATETIME        NOT NULL,
    EXPIRES_AT          DATETIME        NOT NULL,

    -- Auditoría (heredada de Auditable)
    CREATED_BY          VARCHAR(100)    NOT NULL,
    CREATED_DATE        TIMESTAMP       NOT NULL,
    LAST_MODIFIED_BY    VARCHAR(100),
    LAST_MODIFIED_DATE  TIMESTAMP,

    -- Constraints
    CONSTRAINT UK_BLACKLISTED_TOKEN_TOKEN UNIQUE (TOKEN),

    CONSTRAINT FK_BLACKLISTED_TENANT
        FOREIGN KEY (TENANT_ID) REFERENCES DMN_TENANT (CODE)
        ON UPDATE CASCADE

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Índices para mejorar el rendimiento de las consultas
CREATE INDEX IDX_BLACKLISTED_TOKEN_TOKEN   ON AUT_BLACKLISTED_TOKEN (TOKEN);
CREATE INDEX IDX_BLACKLISTED_TOKEN_TENANT  ON AUT_BLACKLISTED_TOKEN (TENANT_ID);
CREATE INDEX IDX_BLACKLISTED_TOKEN_EXPIRY  ON AUT_BLACKLISTED_TOKEN (EXPIRES_AT);
