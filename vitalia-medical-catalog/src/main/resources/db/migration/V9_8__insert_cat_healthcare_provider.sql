-- ============================================================
-- Script: V9_7__insert_healthcare_provider.sql
-- Módulo: vitalia-medical-catalog
-- Descripción: Inserción de Aseguradoras / EPS estándar.
-- ============================================================

INSERT INTO CAT_HEALTHCARE_PROVIDER (CODE, NAME, TAX_ID, EMAIL, PHONE, CREATED_BY) VALUES
('EPS-001', 'Sanitas EPS', '800.123.456-1', 'contact@sanitas.com', '018000919100', 'SYSTEM'),
('EPS-002', 'Sura EPS', '800.987.654-2', 'servicio@sura.com.co', '018000519519', 'SYSTEM'),
('EPS-003', 'Salud Total EPS', '800.555.444-3', 'info@saludtotal.com', '018000114563', 'SYSTEM'),
('EPS-004', 'Nueva EPS', '900.000.111-4', 'atencion@nuevaeps.com', '018000954400', 'SYSTEM'),
('EPS-005', 'Compensar EPS', '860.066.942-5', 'eps@compensar.com', '3077025', 'SYSTEM'),
('EPS-006', 'Coomeva EPS', '800.111.222-6', 'servicios@coomeva.com', '018000950123', 'SYSTEM'),
('EPS-007', 'Famisanar EPS', '830.003.564-7', 'afiliacion@famisanar.com', '3078069', 'SYSTEM'),
('EPS-008', 'Aliansalud EPS', '800.251.444-8', 'aliansalud@colmedica.com', '018000123703', 'SYSTEM'),
('EPS-009', 'Mutual Ser', '806.008.394-9', 'atencion@mutualser.org', '018000114444', 'SYSTEM'),
('EPS-010', 'Caja de Compensación Familiar (Particular)', '000.000.000-0', 'particular@vitalia.com', '0', 'SYSTEM');
