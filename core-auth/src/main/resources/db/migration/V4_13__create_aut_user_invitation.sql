-- ============================================================
-- V4_13__create_aut_user_invitation.sql
-- Tabla de invitaciones de usuarios enviadas por TenantAdmin.
--
-- Ciclo de vida del STATUS:
--   PENDING   → Invitación enviada, esperando que el usuario complete el registro.
--   ACCEPTED  → El usuario completó el formulario de onboarding y fue activado.
--   EXPIRED   → El TTL del token expiró antes de que el usuario aceptara.
--   CANCELLED → El Admin revocó la invitación manualmente.
-- ============================================================

CREATE TABLE IF NOT EXISTS AUT_USER_INVITATION (
    ID              BIGINT PRIMARY KEY AUTO_INCREMENT,

    -- ── Destinatario y contexto ──────────────────────────────
    EMAIL           VARCHAR(100)  NOT NULL,
    FK_ID_TENANT    BIGINT        NOT NULL,
    FK_ID_ROLE      BIGINT        NOT NULL,

    -- ── Token de seguridad (UUID one-time) ───────────────────
    TOKEN           VARCHAR(255)  NOT NULL,
    EXPIRES_AT      DATETIME      NOT NULL,

    -- ── Estado del ciclo de vida ─────────────────────────────
    STATUS          VARCHAR(20)   NOT NULL DEFAULT 'PENDING',
    ACCEPTED_AT     DATETIME      NULL,

    -- ── Auditoría (heredada de Auditable) ─────────────────────
    CREATED_BY          VARCHAR(100)     NOT NULL,
    CREATED_DATE        TIMESTAMP       NOT NULL,
    LAST_MODIFIED_BY    VARCHAR(100),
    LAST_MODIFIED_DATE  TIMESTAMP,

    -- ── Unique Constraints ────────────────────────────────────
    CONSTRAINT UK_INVITATION_TOKEN UNIQUE (TOKEN),

    -- ── Foreign Keys ──────────────────────────────────────────
    CONSTRAINT FK_INVITATION_TENANT
        FOREIGN KEY (FK_ID_TENANT) REFERENCES DMN_TENANT (ID)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    CONSTRAINT FK_INVITATION_ROLE
        FOREIGN KEY (FK_ID_ROLE) REFERENCES AUT_ROLE (ID)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);

-- ── Índices para mejorar el rendimiento de las consultas ──────
CREATE INDEX IDX_INVITATION_EMAIL   ON AUT_USER_INVITATION (EMAIL);
CREATE INDEX IDX_INVITATION_TENANT  ON AUT_USER_INVITATION (FK_ID_TENANT);
CREATE INDEX IDX_INVITATION_STATUS  ON AUT_USER_INVITATION (STATUS);
CREATE INDEX IDX_INVITATION_EXPIRES ON AUT_USER_INVITATION (EXPIRES_AT);
