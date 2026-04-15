-- *******************************************************
-- Script de Refactorización de Invitación (SaaS Elite)
-- *******************************************************
-- Permite guardar metadatos en la invitación sin pre-crear el usuario.

ALTER TABLE AUT_USER_INVITATION ADD COLUMN ROLE_CONTEXT VARCHAR(30) AFTER FK_ID_ROLE;
ALTER TABLE AUT_USER_INVITATION ADD COLUMN NATIONAL_ID VARCHAR(30) AFTER ROLE_CONTEXT;
ALTER TABLE AUT_USER_INVITATION ADD COLUMN EMAIL VARCHAR(120) AFTER NATIONAL_ID;

-- Sincronización de datos existentes
-- 1. Intentamos mapear Role name a RoleContext
UPDATE AUT_USER_INVITATION i 
JOIN AUT_ROLE r ON i.FK_ID_ROLE = r.ID 
SET i.ROLE_CONTEXT = REPLACE(r.NAME, 'ROLE_', '');

-- 2. Email y DNI desde Person si ya existe el usuario
UPDATE AUT_USER_INVITATION i 
JOIN AUT_USER u ON i.FK_ID_USER = u.ID 
JOIN DMN_PERSON p ON u.FK_ID_PERSON = p.ID 
SET i.EMAIL = p.EMAIL, i.NATIONAL_ID = p.NATIONAL_ID;

-- 3. Hacer opcional FK_ID_USER para soportar creación diferida
ALTER TABLE AUT_USER_INVITATION MODIFY COLUMN FK_ID_USER BIGINT NULL;
