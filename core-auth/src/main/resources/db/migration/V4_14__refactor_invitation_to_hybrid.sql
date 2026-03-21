-- ============================================================
-- V4_14__refactor_invitation_to_hybrid.sql
-- Refactorización de la tabla de invitaciones para el Modelo Híbrido.
-- Se vincula directamente a un User pre-creado y se elimina el email redundante.
-- ============================================================

-- 1. Añadir la columna de relación con User
ALTER TABLE AUT_USER_INVITATION 
    ADD COLUMN FK_ID_USER BIGINT NOT NULL AFTER ID;

-- 2. Añadir la Foreign Key
ALTER TABLE AUT_USER_INVITATION 
    ADD CONSTRAINT FK_INVITATION_USER 
    FOREIGN KEY (FK_ID_USER) REFERENCES AUT_USER (ID)
    ON DELETE CASCADE;

-- 3. Crear el índice para FK_ID_USER
CREATE INDEX IDX_INVITATION_USER ON AUT_USER_INVITATION (FK_ID_USER);

-- 4. Eliminar la columna EMAIL y su índice (información ahora residente en AUT_USER)
DROP INDEX IDX_INVITATION_EMAIL ON AUT_USER_INVITATION;
ALTER TABLE AUT_USER_INVITATION DROP COLUMN EMAIL;
