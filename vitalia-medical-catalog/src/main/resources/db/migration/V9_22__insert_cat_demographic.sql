-- ============================================================
-- Script: V9_21__insert_demographic.sql
-- Módulo: vitalia-medical-catalog
-- Descripción: Inserción de Géneros y Estado Civil.
-- ============================================================

INSERT INTO CAT_GENDER (CODE, NAME, CREATED_BY) VALUES
('M', 'Masculino', 'SYSTEM'),
('F', 'Femenino', 'SYSTEM'),
('NB', 'No Binario', 'SYSTEM'),
('UNK', 'Desconocido', 'SYSTEM'),
('OTH', 'Otro', 'SYSTEM');

INSERT INTO CAT_CIVIL_STATUS (CODE, NAME, CREATED_BY) VALUES
('SINGLE', 'Soltero/a', 'SYSTEM'),
('MARRIED', 'Casado/a', 'SYSTEM'),
('DIVORCED', 'Divorciado/a', 'SYSTEM'),
('WIDOWED', 'Viudo/a', 'SYSTEM'),
('UNION', 'Unión Libre', 'SYSTEM'),
('NONE', 'No Reportado', 'SYSTEM');
