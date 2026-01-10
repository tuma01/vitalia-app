-- ============================================================
-- Script: V9_3__insert_medical_procedure.sql
-- Módulo: vitalia-medical-catalog
-- Descripción: Inserción de datos estándar de procedimientos médicos.
-- ============================================================

INSERT INTO CAT_MEDICAL_PROCEDURE (CODE, NAME, TYPE, CREATED_BY) VALUES
-- Laboratorio Clínico
('LAB-001', 'Hemograma completo', 'LABORATORY', 'SYSTEM'),
('LAB-002', 'Glucosa en sangre', 'LABORATORY', 'SYSTEM'),
('LAB-003', 'Creatinina en suero', 'LABORATORY', 'SYSTEM'),
('LAB-004', 'Urianálisis completo', 'LABORATORY', 'SYSTEM'),
('LAB-005', 'Perfil lipídico (Colesterol, LDL, HDL, Triglicéridos)', 'LABORATORY', 'SYSTEM'),
('LAB-006', 'Prueba de embarazo (HCG)', 'LABORATORY', 'SYSTEM'),
('LAB-007', 'Hemoglobina glicosilada (HbA1c)', 'LABORATORY', 'SYSTEM'),
('LAB-008', 'Electrolitos (Sodio, Potasio, Cloro)', 'LABORATORY', 'SYSTEM'),
('LAB-009', 'Tiempo de protrombina (PT)', 'LABORATORY', 'SYSTEM'),
('LAB-010', 'Transaminasa oxalacética (ASAT-GOT)', 'LABORATORY', 'SYSTEM'),

-- Imágenes diagnósticas
('IMG-001', 'Radiografía de tórax (PA y Lateral)', 'RADIOLOGY', 'SYSTEM'),
('IMG-002', 'Ecografía abdominal total', 'RADIOLOGY', 'SYSTEM'),
('IMG-003', 'Tomografía computarizada (TC) de cráneo simple', 'RADIOLOGY', 'SYSTEM'),
('IMG-004', 'Resonancia nuclear magnética (RNM) de columna lumbar', 'RADIOLOGY', 'SYSTEM'),
('IMG-005', 'Mamografía bilateral', 'RADIOLOGY', 'SYSTEM'),
('IMG-006', 'Electrocardiograma de reposo (EKG)', 'RADIOLOGY', 'SYSTEM'),
('IMG-007', 'Ecocardiograma transtorácico', 'RADIOLOGY', 'SYSTEM'),
('IMG-008', 'Densitometría ósea', 'RADIOLOGY', 'SYSTEM'),

-- Procedimientos Quirúrgicos y Otros
('SUR-001', 'Apendicectomía simple', 'SURGERY', 'SYSTEM'),
('SUR-002', 'Colecistectomía por laparoscopia', 'SURGERY', 'SYSTEM'),
('SUR-003', 'Herniorrafia inguinal simple', 'SURGERY', 'SYSTEM'),
('SUR-004', 'Cesárea segmentaria', 'SURGERY', 'SYSTEM'),
('SUR-005', 'Consulta de medicina general', 'CONSULTATION', 'SYSTEM'),
('SUR-006', 'Consulta especializada (Primera vez)', 'CONSULTATION', 'SYSTEM'),
('SUR-007', 'Curación de herida simple', 'PROCEDURE', 'SYSTEM'),
('SUR-008', 'Sutura de herida en piel', 'PROCEDURE', 'SYSTEM');
-- Se pueden añadir cientos más según norma técnica nacional.
