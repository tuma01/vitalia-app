-- ============================================================
-- Script: V9_15__insert_identification_type.sql
-- Módulo: vitalia-medical-catalog
-- Descripción: Inserción de tipos de documento oficiales.
-- ============================================================

INSERT INTO CAT_IDENTIFICATION_TYPE (CODE, NAME, CREATED_BY) VALUES
('CC', 'Cédula de Ciudadanía', 'SYSTEM'),
('CE', 'Cédula de Extranjería', 'SYSTEM'),
('PA', 'Pasaporte', 'SYSTEM'),
('TI', 'Tarjeta de Identidad', 'SYSTEM'),
('RC', 'Registro Civil', 'SYSTEM'),
('CD', 'Carné Diplomático', 'SYSTEM'),
('SC', 'Salvoconducto de Permanencia', 'SYSTEM'),
('PEP', 'Permiso Especial de Permanencia', 'SYSTEM'),
('PPT', 'Permiso por Protección Temporal', 'SYSTEM'),
('NIT', 'Número de Identificación Tributaria', 'SYSTEM'),
('AS', 'Adulto sin Identificación', 'SYSTEM'),
('MS', 'Menor sin Identificación', 'SYSTEM');
-- Estos códigos pueden variar según el país de despliegue.
