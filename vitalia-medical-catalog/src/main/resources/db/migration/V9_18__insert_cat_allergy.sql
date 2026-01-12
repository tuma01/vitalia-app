-- ============================================================
-- Script: V9_17__insert_allergy.sql
-- Módulo: vitalia-medical-catalog
-- Descripción: Alergias comunes (Medicamentos, Alimentos, Ambiental).
-- ============================================================

INSERT INTO CAT_ALLERGY (CODE, NAME, TYPE, CREATED_BY, CREATED_DATE) VALUES
-- Medicamentos
('ALL-001', 'Penicilina y derivados', 'DRUG', 'SYSTEM', CURRENT_TIMESTAMP),
('ALL-002', 'Sulfonamidas (Sulfas)', 'DRUG', 'SYSTEM', CURRENT_TIMESTAMP),
('ALL-003', 'Aspirina y AINEs (Ibuprofeno, Naproxeno)', 'DRUG', 'SYSTEM', CURRENT_TIMESTAMP),
('ALL-004', 'Anticonvulsivantes', 'DRUG', 'SYSTEM', CURRENT_TIMESTAMP),
('ALL-005', 'Insulina', 'DRUG', 'SYSTEM', CURRENT_TIMESTAMP),
('ALL-006', 'Medios de contraste yodados', 'DRUG', 'SYSTEM', CURRENT_TIMESTAMP),

-- Alimentos
('ALL-100', 'Proteína de leche de vaca', 'FOOD', 'SYSTEM', CURRENT_TIMESTAMP),
('ALL-101', 'Huevo', 'FOOD', 'SYSTEM', CURRENT_TIMESTAMP),
('ALL-102', 'Maní (Cacahuetes)', 'FOOD', 'SYSTEM', CURRENT_TIMESTAMP),
('ALL-103', 'Frutos secos (Nueces, Almendras)', 'FOOD', 'SYSTEM', CURRENT_TIMESTAMP),
('ALL-104', 'Pescados y Mariscos', 'FOOD', 'SYSTEM', CURRENT_TIMESTAMP),
('ALL-105', 'Trigo (Gluten)', 'FOOD', 'SYSTEM', CURRENT_TIMESTAMP),
('ALL-106', 'Soya', 'FOOD', 'SYSTEM', CURRENT_TIMESTAMP),

-- Ambiental / Otros
('ALL-200', 'Polen', 'ENVIRONMENTAL', 'SYSTEM', CURRENT_TIMESTAMP),
('ALL-201', 'Ácaros del polvo', 'ENVIRONMENTAL', 'SYSTEM', CURRENT_TIMESTAMP),
('ALL-202', 'Epitelio de animales (Gato, Perro)', 'ENVIRONMENTAL', 'SYSTEM', CURRENT_TIMESTAMP),
('ALL-203', 'Látex', 'OTHER', 'SYSTEM', CURRENT_TIMESTAMP),
('ALL-204', 'Picaduras de insectos (Abeja, Avispa)', 'ENVIRONMENTAL', 'SYSTEM', CURRENT_TIMESTAMP);
