-- ============================================================
-- Script: V9_24__insert_cat_medical_unit_type.sql
-- Módulo: vitalia-medical-catalog
-- Descripción: Inserción de datos maestros con Identidad Global (SaaS Elite Tier)
-- ============================================================

INSERT INTO CAT_MEDICAL_UNIT_TYPE (CODE, NAME, DESCRIPTION, EXTERNAL_ID, VERSION, CREATED_BY, CREATED_DATE) 
VALUES 
('ICU_ADULT', 'Unidad de Cuidados Intensivos Adultos', 'Unidad especializada para pacientes adultos críticos', UUID(), 0, 'SYSTEM', NOW()),
('ICU_NEO', 'Unidad de Cuidados Intensivos Neonatales', 'Unidad para recién nacidos de alto riesgo', UUID(), 0, 'SYSTEM', NOW()),
('OR', 'Quirófano / Sala de Operaciones', 'Área destinada a procedimientos quirúrgicos', UUID(), 0, 'SYSTEM', NOW()),
('EMERGENCY', 'Departamento de Emergencias', 'Atención inmediata y tripartizaje', UUID(), 0, 'SYSTEM', NOW()),
('PEDIATRICS', 'Unidad de Pediatría', 'Atención especializada para niños y adolescentes', UUID(), 0, 'SYSTEM', NOW()),
('CARDIOLOGY', 'Unidad de Cardiología', 'Servicio especializado en salud cardiovascular', UUID(), 0, 'SYSTEM', NOW()),
('MATERNITY', 'Unidad de Maternidad', 'Atención a embarazadas y obstetricia', UUID(), 0, 'SYSTEM', NOW()),
('RECOVERY', 'Sala de Recuperación', 'Área post-anestésica y post-quirúrgica', UUID(), 0, 'SYSTEM', NOW()),
('DIALYSIS', 'Unidad de Diálisis', 'Tratamiento de insuficiencia renal', UUID(), 0, 'SYSTEM', NOW()),
('ONCOLOGY', 'Unidad de Oncología', 'Tratamiento y seguimiento de pacientes con cáncer', UUID(), 0, 'SYSTEM', NOW());
