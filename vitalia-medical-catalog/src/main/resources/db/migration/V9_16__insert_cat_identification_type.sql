-- ============================================================
-- Script: V9_15__insert_identification_type.sql
-- Módulo: vitalia-medical-catalog
-- Descripción: Inserción de tipos de documento oficiales.
-- ============================================================

INSERT INTO CAT_IDENTIFICATION_TYPE (CODE, NAME, CREATED_BY, CREATED_DATE, VERSION, EXTERNAL_ID) VALUES
('CC', 'Cédula de Ciudadanía', 'SYSTEM', CURRENT_TIMESTAMP, 0, 'c9c2be92-17fd-4f8f-a357-d00d5214e90a'),
('CE', 'Cédula de Extranjería', 'SYSTEM', CURRENT_TIMESTAMP, 0, '34c71c83-c6c4-4c1d-b627-e9580cc7400c'),
('PA', 'Pasaporte', 'SYSTEM', CURRENT_TIMESTAMP, 0, '19302d7a-83b6-4119-aefc-01f6151ae910'),
('TI', 'Tarjeta de Identidad', 'SYSTEM', CURRENT_TIMESTAMP, 0, 'df58c0a2-53ad-456e-9786-e21611ea31fd'),
('RC', 'Registro Civil', 'SYSTEM', CURRENT_TIMESTAMP, 0, '87f43ed7-24e0-4d02-81ee-956a5e607c88'),
('CD', 'Carné Diplomático', 'SYSTEM', CURRENT_TIMESTAMP, 0, '26077557-dc40-466e-8222-d76c52c78f36'),
('SC', 'Salvoconducto de Permanencia', 'SYSTEM', CURRENT_TIMESTAMP, 0, 'c58200ff-0fbc-4ce9-8211-83ca66b3f253'),
('PEP', 'Permiso Especial de Permanencia', 'SYSTEM', CURRENT_TIMESTAMP, 0, '9ec13246-14bf-49a0-8868-fe497f55f85c'),
('PPT', 'Permiso por Protección Temporal', 'SYSTEM', CURRENT_TIMESTAMP, 0, '32caeab6-b3f9-441b-9472-aba8467a876f'),
('NIT', 'Número de Identificación Tributaria', 'SYSTEM', CURRENT_TIMESTAMP, 0, '2bda6179-9a2e-4ac2-9a96-1966d940857e'),
('AS', 'Adulto sin Identificación', 'SYSTEM', CURRENT_TIMESTAMP, 0, '035235d5-77c7-4669-89fd-73cb52e5eeda'),
('MS', 'Menor sin Identificación', 'SYSTEM', CURRENT_TIMESTAMP, 0, 'c574d389-8f4b-42d2-a713-e8b85f273cc8');
-- Estos códigos pueden variar según el país de despliegue.);
