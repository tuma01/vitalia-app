-- ============================================================
-- Script: V9_13__insert_kinship.sql
-- Módulo: vitalia-medical-catalog
-- Descripción: Inserción de parentescos estándar.
-- ============================================================

INSERT INTO CAT_KINSHIP (CODE, NAME, CREATED_BY) VALUES
('FATHER', 'Padre', 'SYSTEM'),
('MOTHER', 'Madre', 'SYSTEM'),
('SPOUSE', 'Cónyuge / Compañero(a)', 'SYSTEM'),
('SON', 'Hijo', 'SYSTEM'),
('DAUGHTER', 'Hija', 'SYSTEM'),
('BROTHER', 'Hermano', 'SYSTEM'),
('SISTER', 'Hermana', 'SYSTEM'),
('GRANDFATHER', 'Abuelo', 'SYSTEM'),
('GRANDMOTHER', 'Abuela', 'SYSTEM'),
('UNCLE', 'Tío', 'SYSTEM'),
('AUNT', 'Tía', 'SYSTEM'),
('COUSIN', 'Primo / Prima', 'SYSTEM'),
('OTHER', 'Otro', 'SYSTEM'),
('NONE', 'Ninguno', 'SYSTEM');
