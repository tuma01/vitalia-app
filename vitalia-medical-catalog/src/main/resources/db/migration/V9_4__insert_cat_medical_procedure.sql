-- ============================================================
-- Script: V9_3__insert_medical_procedure.sql
-- Módulo: vitalia-medical-catalog
-- Descripción: Inserción de datos estándar de procedimientos médicos.
-- ============================================================

INSERT INTO CAT_MEDICAL_PROCEDURE (CODE, NAME, TYPE, CREATED_BY, CREATED_DATE) VALUES
-- Laboratorio Clínico
('LAB-001', 'Hemograma completo', 'LABORATORY', 'SYSTEM', CURRENT_TIMESTAMP),
('LAB-002', 'Glucosa en sangre', 'LABORATORY', 'SYSTEM', CURRENT_TIMESTAMP),
('LAB-003', 'Creatinina en suero', 'LABORATORY', 'SYSTEM', CURRENT_TIMESTAMP),
('LAB-004', 'Urianálisis completo', 'LABORATORY', 'SYSTEM', CURRENT_TIMESTAMP),
('LAB-005', 'Perfil lipídico (Colesterol, LDL, HDL, Triglicéridos)', 'LABORATORY', 'SYSTEM', CURRENT_TIMESTAMP),
('LAB-006', 'Prueba de embarazo (HCG)', 'LABORATORY', 'SYSTEM', CURRENT_TIMESTAMP),
('LAB-007', 'Hemoglobina glicosilada (HbA1c)', 'LABORATORY', 'SYSTEM', CURRENT_TIMESTAMP),
('LAB-008', 'Electrolitos (Sodio, Potasio, Cloro)', 'LABORATORY', 'SYSTEM', CURRENT_TIMESTAMP),
('LAB-009', 'Tiempo de protrombina (PT)', 'LABORATORY', 'SYSTEM', CURRENT_TIMESTAMP),
('LAB-010', 'Transaminasa oxalacética (ASAT-GOT)', 'LABORATORY', 'SYSTEM', CURRENT_TIMESTAMP),
('LAB-011', 'Transaminasa pirúvica (ALAT-GPT)', 'LABORATORY', 'SYSTEM', CURRENT_TIMESTAMP),
('LAB-012', 'Proteína C Reactiva (PCR)', 'LABORATORY', 'SYSTEM', CURRENT_TIMESTAMP),
('LAB-013', 'Perfil Tiroideo (TSH, T3, T4)', 'LABORATORY', 'SYSTEM', CURRENT_TIMESTAMP),
('LAB-014', 'Ácido Úrico en suero', 'LABORATORY', 'SYSTEM', CURRENT_TIMESTAMP),
('LAB-015', 'Coprológico por montaje directo', 'LABORATORY', 'SYSTEM', CURRENT_TIMESTAMP),

-- Imágenes diagnósticas
('IMG-001', 'Radiografía de tórax (PA y Lateral)', 'RADIOLOGY', 'SYSTEM', CURRENT_TIMESTAMP),
('IMG-002', 'Ecografía abdominal total', 'RADIOLOGY', 'SYSTEM', CURRENT_TIMESTAMP),
('IMG-003', 'Tomografía computarizada (TC) de cráneo simple', 'RADIOLOGY', 'SYSTEM', CURRENT_TIMESTAMP),
('IMG-004', 'Resonancia nuclear magnética (RNM) de columna lumbar', 'RADIOLOGY', 'SYSTEM', CURRENT_TIMESTAMP),
('IMG-005', 'Mamografía bilateral', 'RADIOLOGY', 'SYSTEM', CURRENT_TIMESTAMP),
('IMG-006', 'Electrocardiograma de reposo (EKG)', 'RADIOLOGY', 'SYSTEM', CURRENT_TIMESTAMP),
('IMG-007', 'Ecocardiograma transtorácico', 'RADIOLOGY', 'SYSTEM', CURRENT_TIMESTAMP),
('IMG-008', 'Densitometría ósea', 'RADIOLOGY', 'SYSTEM', CURRENT_TIMESTAMP),
('IMG-009', 'Ecografía obstétrica', 'RADIOLOGY', 'SYSTEM', CURRENT_TIMESTAMP),
('IMG-010', 'TC de Abdomen y Pelvis con contraste', 'RADIOLOGY', 'SYSTEM', CURRENT_TIMESTAMP),
('IMG-011', 'Ecografía de tejidos blandos', 'RADIOLOGY', 'SYSTEM', CURRENT_TIMESTAMP),

-- Procedimientos Quirúrgicos y Otros
('SUR-001', 'Apendicectomía simple', 'SURGERY', 'SYSTEM', CURRENT_TIMESTAMP),
('SUR-002', 'Colecistectomía por laparoscopia', 'SURGERY', 'SYSTEM', CURRENT_TIMESTAMP),
('SUR-003', 'Herniorrafia inguinal simple', 'SURGERY', 'SYSTEM', CURRENT_TIMESTAMP),
('SUR-004', 'Cesárea segmentaria', 'SURGERY', 'SYSTEM', CURRENT_TIMESTAMP),
('SUR-005', 'Consulta de medicina general', 'CONSULTATION', 'SYSTEM', CURRENT_TIMESTAMP),
('SUR-006', 'Consulta especializada (Primera vez)', 'CONSULTATION', 'SYSTEM', CURRENT_TIMESTAMP),
('SUR-007', 'Curación de herida simple', 'PROCEDURE', 'SYSTEM', CURRENT_TIMESTAMP),
('SUR-008', 'Sutura de herida en piel', 'PROCEDURE', 'SYSTEM', CURRENT_TIMESTAMP),
('SUR-009', 'Cateterismo cardíaco izquierdo', 'PROCEDURE', 'SYSTEM', CURRENT_TIMESTAMP),
('SUR-010', 'Endoscopia digestiva superior', 'PROCEDURE', 'SYSTEM', CURRENT_TIMESTAMP),
('SUR-011', 'Colonoscopia total', 'PROCEDURE', 'SYSTEM', CURRENT_TIMESTAMP),
('SUR-012', 'Lavado peritoneal diagnóstico', 'PROCEDURE', 'SYSTEM', CURRENT_TIMESTAMP),
('SUR-013', 'Biopsia de piel por punch', 'PROCEDURE', 'SYSTEM', CURRENT_TIMESTAMP);
-- Se pueden añadir cientos más según norma técnica nacional.
