-- ============================================================
-- Script: V9_26__insert_cat_medical_consultation_type.sql
-- Módulo: vitalia-medical-catalog
-- Descripción: Inserción de tipos de consulta estándar (SaaS Elite Tier)
-- ============================================================

INSERT INTO CAT_MEDICAL_CONSULTATION_TYPE (CODE, NAME, DESCRIPTION, EXTERNAL_ID, VERSION, CREATED_BY, CREATED_DATE) 
VALUES 
('GEN_CHECKUP', 'Consulta General / Chequeo', 'Evaluación médica rutinaria y preventiva', UUID(), 0, 'SYSTEM', NOW()),
('FOLLOW_UP', 'Consulta de Seguimiento', 'Control posterior a diagnóstico o tratamiento', UUID(), 0, 'SYSTEM', NOW()),
('EMERGENCY', 'Consulta de Emergencia', 'Atención inmediata por cuadro agudo', UUID(), 0, 'SYSTEM', NOW()),
('TELEMED', 'Telemedicina / Videoconsulta', 'Consulta remota vía plataformas digitales', UUID(), 0, 'SYSTEM', NOW()),
('SPECIALIST', 'Consulta con Especialista', 'Atención derivada a rama médica específica', UUID(), 0, 'SYSTEM', NOW()),
('HOME_VISIT', 'Visita Domiciliaria', 'Atención médica en la residencia del paciente', UUID(), 0, 'SYSTEM', NOW()),
('PROCEDURE', 'Consulta para Procedimientos', 'Cita destinada a actos técnicos o quirúrgicos menores', UUID(), 0, 'SYSTEM', NOW()),
('PROC_RESULT', 'Entrega de Resultados', 'Consulta para revisión de exámenes y laboratorios', UUID(), 0, 'SYSTEM', NOW());
