-- ============================================================
-- Script: V9_19__insert_vaccine.sql
-- Módulo: vitalia-medical-catalog
-- Descripción: Esquema básico de vacunación.
-- ============================================================

INSERT INTO CAT_VACCINE (CODE, NAME, CREATED_BY, CREATED_DATE) VALUES
('VAC-001', 'BCG (Antituberculosa)', 'SYSTEM', CURRENT_TIMESTAMP),
('VAC-002', 'Hepatitis B (Recién nacido/Adulto)', 'SYSTEM', CURRENT_TIMESTAMP),
('VAC-003', 'Pentavalente (DPT+HB+Hib)', 'SYSTEM', CURRENT_TIMESTAMP),
('VAC-004', 'Polio (Oral o Inyectable)', 'SYSTEM', CURRENT_TIMESTAMP),
('VAC-005', 'Rotavirus', 'SYSTEM', CURRENT_TIMESTAMP),
('VAC-006', 'Neumococo', 'SYSTEM', CURRENT_TIMESTAMP),
('VAC-007', 'Influenza Estacional', 'SYSTEM', CURRENT_TIMESTAMP),
('VAC-008', 'Triple Viral (SRP - Sarampión, Rubéola, Parotiditis)', 'SYSTEM', CURRENT_TIMESTAMP),
('VAC-009', 'Hepatitis A', 'SYSTEM', CURRENT_TIMESTAMP),
('VAC-010', 'Varicela', 'SYSTEM', CURRENT_TIMESTAMP),
('VAC-011', 'Fiebre Amarilla', 'SYSTEM', CURRENT_TIMESTAMP),
('VAC-012', 'VPH (Virus del Papiloma Humano)', 'SYSTEM', CURRENT_TIMESTAMP),
('VAC-013', 'DPT (Difteria, Tétanos, Tos Ferina)', 'SYSTEM', CURRENT_TIMESTAMP),
('VAC-014', 'Toxoide Tetánico Diferico (Td)', 'SYSTEM', CURRENT_TIMESTAMP),
('VAC-015', 'COVID-19 (Esquema completo)', 'SYSTEM', CURRENT_TIMESTAMP);
