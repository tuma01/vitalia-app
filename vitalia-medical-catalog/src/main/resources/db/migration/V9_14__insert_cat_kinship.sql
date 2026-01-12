-- ============================================================
-- Script: V9_13__insert_kinship.sql
-- Módulo: vitalia-medical-catalog
-- Descripción: Inserción de parentescos estándar.
-- ============================================================

INSERT INTO CAT_KINSHIP (CODE, NAME, CREATED_BY, CREATED_DATE) VALUES
('FATHER', 'Padre', 'SYSTEM', CURRENT_TIMESTAMP),
('MOTHER', 'Madre', 'SYSTEM', CURRENT_TIMESTAMP),
('SPOUSE', 'Cónyuge / Compañero(a)', 'SYSTEM', CURRENT_TIMESTAMP),
('SON', 'Hijo', 'SYSTEM', CURRENT_TIMESTAMP),
('DAUGHTER', 'Hija', 'SYSTEM', CURRENT_TIMESTAMP),
('BROTHER', 'Hermano', 'SYSTEM', CURRENT_TIMESTAMP),
('SISTER', 'Hermana', 'SYSTEM', CURRENT_TIMESTAMP),
('GRANDFATHER', 'Abuelo', 'SYSTEM', CURRENT_TIMESTAMP),
('GRANDMOTHER', 'Abuela', 'SYSTEM', CURRENT_TIMESTAMP),
('UNCLE', 'Tío', 'SYSTEM', CURRENT_TIMESTAMP),
('AUNT', 'Tía', 'SYSTEM', CURRENT_TIMESTAMP),
('COUSIN', 'Primo / Prima', 'SYSTEM', CURRENT_TIMESTAMP),
('OTHER', 'Otro', 'SYSTEM', CURRENT_TIMESTAMP),
('NONE', 'Ninguno', 'SYSTEM', CURRENT_TIMESTAMP);
