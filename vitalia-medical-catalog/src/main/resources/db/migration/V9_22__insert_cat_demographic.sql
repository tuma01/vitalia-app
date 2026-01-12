-- ============================================================
-- Script: V9_21__insert_demographic.sql
-- Módulo: vitalia-medical-catalog
-- Descripción: Inserción de Géneros y Estado Civil.
-- ============================================================

INSERT INTO CAT_GENDER (CODE, NAME, CREATED_BY, CREATED_DATE) VALUES
('M', 'Masculino', 'SYSTEM', CURRENT_TIMESTAMP),
('F', 'Femenino', 'SYSTEM', CURRENT_TIMESTAMP),
('NB', 'No Binario', 'SYSTEM', CURRENT_TIMESTAMP),
('UNK', 'Desconocido', 'SYSTEM', CURRENT_TIMESTAMP),
('OTH', 'Otro', 'SYSTEM', CURRENT_TIMESTAMP);

INSERT INTO CAT_CIVIL_STATUS (CODE, NAME, CREATED_BY, CREATED_DATE) VALUES
('SINGLE', 'Soltero/a', 'SYSTEM', CURRENT_TIMESTAMP),
('MARRIED', 'Casado/a', 'SYSTEM', CURRENT_TIMESTAMP),
('DIVORCED', 'Divorciado/a', 'SYSTEM', CURRENT_TIMESTAMP),
('WIDOWED', 'Viudo/a', 'SYSTEM', CURRENT_TIMESTAMP),
('UNION', 'Unión Libre', 'SYSTEM', CURRENT_TIMESTAMP),
('NONE', 'No Reportado', 'SYSTEM', CURRENT_TIMESTAMP);
