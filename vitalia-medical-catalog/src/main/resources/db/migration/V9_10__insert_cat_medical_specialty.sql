-- ============================================================
-- Script: V9_9__insert_medical_specialty.sql
-- Módulo: vitalia-medical-catalog
-- Descripción: Inserción de especialidades médicas y de enfermería.
-- ============================================================

INSERT INTO CAT_MEDICAL_SPECIALTY (CODE, NAME, TARGET_PROFESSION, CREATED_BY, CREATED_DATE) VALUES
-- Medicina
('ESP-001', 'Cardiología', 'DOCTOR', 'SYSTEM', CURRENT_TIMESTAMP),
('ESP-002', 'Pediatría', 'DOCTOR', 'SYSTEM', CURRENT_TIMESTAMP),
('ESP-003', 'Ginecología y Obstetricia', 'DOCTOR', 'SYSTEM', CURRENT_TIMESTAMP),
('ESP-004', 'Medicina Interna', 'DOCTOR', 'SYSTEM', CURRENT_TIMESTAMP),
('ESP-005', 'Dermatología', 'DOCTOR', 'SYSTEM', CURRENT_TIMESTAMP),
('ESP-006', 'Neurología', 'DOCTOR', 'SYSTEM', CURRENT_TIMESTAMP),
('ESP-007', 'Psiquiatría', 'DOCTOR', 'SYSTEM', CURRENT_TIMESTAMP),
('ESP-008', 'Oftalmología', 'DOCTOR', 'SYSTEM', CURRENT_TIMESTAMP),
('ESP-009', 'Otorrinolaringología', 'DOCTOR', 'SYSTEM', CURRENT_TIMESTAMP),
('ESP-010', 'Ortopedia y Traumatología', 'DOCTOR', 'SYSTEM', CURRENT_TIMESTAMP),
('ESP-011', 'Anestesiología', 'DOCTOR', 'SYSTEM', CURRENT_TIMESTAMP),
('ESP-012', 'Urología', 'DOCTOR', 'SYSTEM', CURRENT_TIMESTAMP),
('ESP-013', 'Gastroenterología', 'DOCTOR', 'SYSTEM', CURRENT_TIMESTAMP),
('ESP-014', 'Endocrinología', 'DOCTOR', 'SYSTEM', CURRENT_TIMESTAMP),
('ESP-015', 'Nefrología', 'DOCTOR', 'SYSTEM', CURRENT_TIMESTAMP),
('ESP-016', 'Cirugía General', 'DOCTOR', 'SYSTEM', CURRENT_TIMESTAMP),
('ESP-017', 'Medicina Familiar', 'DOCTOR', 'SYSTEM', CURRENT_TIMESTAMP),
('ESP-018', 'Medicina de Urgencias', 'DOCTOR', 'SYSTEM', CURRENT_TIMESTAMP),
('ESP-019', 'Radiología e Imágenes Diagnósticas', 'DOCTOR', 'SYSTEM', CURRENT_TIMESTAMP),
('ESP-020', 'Patología Clínica', 'DOCTOR', 'SYSTEM', CURRENT_TIMESTAMP),

-- Enfermería
('ENF-001', 'Enfermería en Cuidado Crítico', 'NURSE', 'SYSTEM', CURRENT_TIMESTAMP),
('ENF-002', 'Enfermería Nefrológica', 'NURSE', 'SYSTEM', CURRENT_TIMESTAMP),
('ENF-003', 'Enfermería Oncológica', 'NURSE', 'SYSTEM', CURRENT_TIMESTAMP),
('ENF-004', 'Enfermería Pediátrica', 'NURSE', 'SYSTEM', CURRENT_TIMESTAMP),
('ENF-005', 'Enfermería de Salud Mental', 'NURSE', 'SYSTEM', CURRENT_TIMESTAMP),
('ENF-006', 'Enfermería Obstétrico-Ginecológica', 'NURSE', 'SYSTEM', CURRENT_TIMESTAMP),
('ENF-007', 'Enfermería en Salud Pública', 'BOTH', 'SYSTEM', CURRENT_TIMESTAMP),
('ENF-008', 'Administración en Salud', 'BOTH', 'SYSTEM', CURRENT_TIMESTAMP);
-- Lista extensible según normativas de cada país.
