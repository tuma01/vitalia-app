-- ============================================================
-- Script: V9_17__insert_allergy.sql
-- Módulo: vitalia-medical-catalog
-- Descripción: Alergias comunes (Medicamentos, Alimentos, Ambiental).
-- ============================================================

INSERT INTO CAT_ALLERGY (CODE, NAME, TYPE, CREATED_BY) VALUES
-- Medicamentos
('ALL-001', 'Penicilina y derivados', 'DRUG', 'SYSTEM'),
('ALL-002', 'Sulfonamidas (Sulfas)', 'DRUG', 'SYSTEM'),
('ALL-003', 'Aspirina y AINEs (Ibuprofeno, Naproxeno)', 'DRUG', 'SYSTEM'),
('ALL-004', 'Anticonvulsivantes', 'DRUG', 'SYSTEM'),
('ALL-005', 'Insulina', 'DRUG', 'SYSTEM'),
('ALL-006', 'Medios de contraste yodados', 'DRUG', 'SYSTEM'),

-- Alimentos
('ALL-100', 'Proteína de leche de vaca', 'FOOD', 'SYSTEM'),
('ALL-101', 'Huevo', 'FOOD', 'SYSTEM'),
('ALL-102', 'Maní (Cacahuetes)', 'FOOD', 'SYSTEM'),
('ALL-103', 'Frutos secos (Nueces, Almendras)', 'FOOD', 'SYSTEM'),
('ALL-104', 'Pescados y Mariscos', 'FOOD', 'SYSTEM'),
('ALL-105', 'Trigo (Gluten)', 'FOOD', 'SYSTEM'),
('ALL-106', 'Soya', 'FOOD', 'SYSTEM'),

-- Ambiental / Otros
('ALL-200', 'Polen', 'ENVIRONMENTAL', 'SYSTEM'),
('ALL-201', 'Ácaros del polvo', 'ENVIRONMENTAL', 'SYSTEM'),
('ALL-202', 'Epitelio de animales (Gato, Perro)', 'ENVIRONMENTAL', 'SYSTEM'),
('ALL-203', 'Látex', 'OTHER', 'SYSTEM'),
('ALL-204', 'Picaduras de insectos (Abeja, Avispa)', 'ENVIRONMENTAL', 'SYSTEM');
