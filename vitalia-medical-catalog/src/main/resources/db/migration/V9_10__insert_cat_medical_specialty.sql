-- ============================================================
-- Script: V9_9__insert_medical_specialty.sql
-- Módulo: vitalia-medical-catalog
-- Descripción: Inserción de especialidades médicas y de enfermería.
-- ============================================================

INSERT INTO CAT_MEDICAL_SPECIALTY (CODE, NAME, TARGET_PROFESSION, CREATED_BY) VALUES
-- Medicina
('ESP-001', 'Cardiología', 'DOCTOR', 'SYSTEM'),
('ESP-002', 'Pediatría', 'DOCTOR', 'SYSTEM'),
('ESP-003', 'Ginecología y Obstetricia', 'DOCTOR', 'SYSTEM'),
('ESP-004', 'Medicina Interna', 'DOCTOR', 'SYSTEM'),
('ESP-005', 'Dermatología', 'DOCTOR', 'SYSTEM'),
('ESP-006', 'Neurología', 'DOCTOR', 'SYSTEM'),
('ESP-007', 'Psiquiatría', 'DOCTOR', 'SYSTEM'),
('ESP-008', 'Oftalmología', 'DOCTOR', 'SYSTEM'),
('ESP-009', 'Otorrinolaringología', 'DOCTOR', 'SYSTEM'),
('ESP-010', 'Ortopedia y Traumatología', 'DOCTOR', 'SYSTEM'),
('ESP-011', 'Anestesiología', 'DOCTOR', 'SYSTEM'),
('ESP-012', 'Urología', 'DOCTOR', 'SYSTEM'),
('ESP-013', 'Gastroenterología', 'DOCTOR', 'SYSTEM'),
('ESP-014', 'Endocrinología', 'DOCTOR', 'SYSTEM'),
('ESP-015', 'Nefrología', 'DOCTOR', 'SYSTEM'),
('ESP-016', 'Cirugía General', 'DOCTOR', 'SYSTEM'),
('ESP-017', 'Medicina Familiar', 'DOCTOR', 'SYSTEM'),
('ESP-018', 'Medicina de Urgencias', 'DOCTOR', 'SYSTEM'),
('ESP-019', 'Radiología e Imágenes Diagnósticas', 'DOCTOR', 'SYSTEM'),
('ESP-020', 'Patología Clínica', 'DOCTOR', 'SYSTEM'),

-- Enfermería
('ENF-001', 'Enfermería en Cuidado Crítico', 'NURSE', 'SYSTEM'),
('ENF-002', 'Enfermería Nefrológica', 'NURSE', 'SYSTEM'),
('ENF-003', 'Enfermería Oncológica', 'NURSE', 'SYSTEM'),
('ENF-004', 'Enfermería Pediátrica', 'NURSE', 'SYSTEM'),
('ENF-005', 'Enfermería de Salud Mental', 'NURSE', 'SYSTEM'),
('ENF-006', 'Enfermería Obstétrico-Ginecológica', 'NURSE', 'SYSTEM'),
('ENF-007', 'Enfermería en Salud Pública', 'BOTH', 'SYSTEM'),
('ENF-008', 'Administración en Salud', 'BOTH', 'SYSTEM');
-- Lista extensible según normativas de cada país.
