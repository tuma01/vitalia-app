-- ============================================================
-- Script: V9_11__insert_blood_type.sql
-- Módulo: vitalia-medical-catalog
-- Descripción: Inserción de todos los grupos sanguíneos.
-- ============================================================

INSERT INTO CAT_BLOOD_TYPE (CODE, NAME, CREATED_BY) VALUES
('A+', 'A Positivo', 'SYSTEM'),
('A-', 'A Negativo', 'SYSTEM'),
('B+', 'B Positivo', 'SYSTEM'),
('B-', 'B Negativo', 'SYSTEM'),
('AB+', 'AB Positivo', 'SYSTEM'),
('AB-', 'AB Negativo', 'SYSTEM'),
('O+', 'O Positivo', 'SYSTEM'),
('O-', 'O Negativo', 'SYSTEM');
