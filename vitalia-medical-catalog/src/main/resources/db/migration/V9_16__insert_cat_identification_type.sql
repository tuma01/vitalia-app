-- ============================================================
-- Script: V9_15__insert_identification_type.sql
-- Módulo: vitalia-medical-catalog
-- Descripción: Inserción de tipos de documento oficiales.
-- ============================================================

INSERT INTO CAT_IDENTIFICATION_TYPE (CODE, NAME, CREATED_BY, CREATED_DATE) VALUES
('CC', 'Cédula de Ciudadanía', 'SYSTEM', CURRENT_TIMESTAMP),
('CE', 'Cédula de Extranjería', 'SYSTEM', CURRENT_TIMESTAMP),
('PA', 'Pasaporte', 'SYSTEM', CURRENT_TIMESTAMP),
('TI', 'Tarjeta de Identidad', 'SYSTEM', CURRENT_TIMESTAMP),
('RC', 'Registro Civil', 'SYSTEM', CURRENT_TIMESTAMP),
('CD', 'Carné Diplomático', 'SYSTEM', CURRENT_TIMESTAMP),
('SC', 'Salvoconducto de Permanencia', 'SYSTEM', CURRENT_TIMESTAMP),
('PEP', 'Permiso Especial de Permanencia', 'SYSTEM', CURRENT_TIMESTAMP),
('PPT', 'Permiso por Protección Temporal', 'SYSTEM', CURRENT_TIMESTAMP),
('NIT', 'Número de Identificación Tributaria', 'SYSTEM', CURRENT_TIMESTAMP),
('AS', 'Adulto sin Identificación', 'SYSTEM', CURRENT_TIMESTAMP),
('MS', 'Menor sin Identificación', 'SYSTEM', CURRENT_TIMESTAMP);
-- Estos códigos pueden variar según el país de despliegue.
