-- ============================================================
-- V4_9__create_aut_refresh_token.sql
-- Tabla para tokens de refresco de sesión.
-- Posee aislamiento nativo multi-tenant (Elite Tier).
-- ============================================================

CREATE TABLE IF NOT EXISTS AUT_REFRESH_TOKEN (
    ID                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    TENANT_ID           VARCHAR(50)     NOT NULL,
    EXTERNAL_ID         VARCHAR(36)     UNIQUE,
    VERSION             BIGINT          DEFAULT 0 NOT NULL,
    IS_DELETED          TINYINT(1)      DEFAULT 0 NOT NULL,

    TOKEN               VARCHAR(255)    NOT NULL,
    USER_ID             BIGINT          NOT NULL,

    EXPIRY_DATE         DATETIME        NOT NULL,
    REVOKED             TINYINT(1)      DEFAULT 0 NOT NULL,

    -- Auditoría (heredada de Auditable)
    CREATED_BY          VARCHAR(100)    NOT NULL,
    CREATED_DATE        TIMESTAMP       NOT NULL,
    LAST_MODIFIED_BY    VARCHAR(100),
    LAST_MODIFIED_DATE  TIMESTAMP,

    -- Constraints
    CONSTRAINT UK_REFRESH_TOKEN_TOKEN   UNIQUE (TOKEN),
    
    CONSTRAINT FK_REFRESH_TOKEN_USER
        FOREIGN KEY (USER_ID) REFERENCES AUT_USER (ID)
        ON DELETE CASCADE,

    CONSTRAINT FK_REFRESH_TOKEN_TENANT
        FOREIGN KEY (TENANT_ID) REFERENCES DMN_TENANT (CODE)
        ON DELETE CASCADE
        ON UPDATE CASCADE

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Índices para mejorar el rendimiento de las consultas
CREATE INDEX IDX_REFRESH_TOKEN_USER    ON AUT_REFRESH_TOKEN (USER_ID);
CREATE INDEX IDX_REFRESH_TOKEN_TENANT  ON AUT_REFRESH_TOKEN (TENANT_ID);
CREATE INDEX IDX_REFRESH_TOKEN_TOKEN   ON AUT_REFRESH_TOKEN (TOKEN);
