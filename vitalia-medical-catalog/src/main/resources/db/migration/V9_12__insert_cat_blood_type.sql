-- ============================================================
-- Script: V9_11__insert_blood_type.sql
-- Módulo: vitalia-medical-catalog
-- Descripción: Inserción de todos los grupos sanguíneos.
-- ============================================================

INSERT INTO CAT_BLOOD_TYPE (CODE, NAME, CREATED_BY, CREATED_DATE) VALUES
('A+', 'A Positivo', 'SYSTEM', CURRENT_TIMESTAMP),
('A-', 'A Negativo', 'SYSTEM', CURRENT_TIMESTAMP),
('B+', 'B Positivo', 'SYSTEM', CURRENT_TIMESTAMP),
('B-', 'B Negativo', 'SYSTEM', CURRENT_TIMESTAMP),
('AB+', 'AB Positivo', 'SYSTEM', CURRENT_TIMESTAMP),
('AB-', 'AB Negativo', 'SYSTEM', CURRENT_TIMESTAMP),
('O+', 'O Positivo', 'SYSTEM', CURRENT_TIMESTAMP),
('O-', 'O Negativo', 'SYSTEM', CURRENT_TIMESTAMP);
