-- ============================================================
-- Script: V9_7__insert_healthcare_provider.sql
-- Módulo: vitalia-medical-catalog
-- Descripción: Inserción de Aseguradoras / EPS estándar.
-- ============================================================

INSERT INTO CAT_HEALTHCARE_PROVIDER (CODE, NAME, TAX_ID, EMAIL, PHONE, CREATED_BY, CREATED_DATE) VALUES
('EPS-001', 'Sanitas EPS', '800.123.456-1', 'contact@sanitas.com', '018000919100', 'SYSTEM', CURRENT_TIMESTAMP),
('EPS-002', 'Sura EPS', '800.987.654-2', 'servicio@sura.com.co', '018000519519', 'SYSTEM', CURRENT_TIMESTAMP),
('EPS-003', 'Salud Total EPS', '800.555.444-3', 'info@saludtotal.com', '018000114563', 'SYSTEM', CURRENT_TIMESTAMP),
('EPS-004', 'Nueva EPS', '900.000.111-4', 'atencion@nuevaeps.com', '018000954400', 'SYSTEM', CURRENT_TIMESTAMP),
('EPS-005', 'Compensar EPS', '860.066.942-5', 'eps@compensar.com', '3077025', 'SYSTEM', CURRENT_TIMESTAMP),
('EPS-006', 'Coomeva EPS', '800.111.222-6', 'servicios@coomeva.com', '018000950123', 'SYSTEM', CURRENT_TIMESTAMP),
('EPS-007', 'Famisanar EPS', '830.003.564-7', 'afiliacion@famisanar.com', '3078069', 'SYSTEM', CURRENT_TIMESTAMP),
('EPS-008', 'Aliansalud EPS', '800.251.444-8', 'aliansalud@colmedica.com', '018000123703', 'SYSTEM', CURRENT_TIMESTAMP),
('EPS-009', 'Mutual Ser', '806.008.394-9', 'atencion@mutualser.org', '018000114444', 'SYSTEM', CURRENT_TIMESTAMP),
('EPS-010', 'Particular / Sin Aseguradora', '000.000.000-0', 'particular@vitalia.com', '0', 'SYSTEM', CURRENT_TIMESTAMP),
('EPS-011', 'Cajasan', '890.201.235-0', 'contacto@cajasan.com', '018000123456', 'SYSTEM', CURRENT_TIMESTAMP),
('EPS-012', 'Comfenalco', '890.900.842-6', 'servicios@comfenalco.com', '018000912233', 'SYSTEM', CURRENT_TIMESTAMP),
('EPS-013', 'Cafesalud (Liquidada)', '800.140.881-1', 'archivo@cafesalud.com', '0', 'SYSTEM', CURRENT_TIMESTAMP),
('EPS-014', 'Saludvida (Liquidada)', '830.074.839-2', 'info@saludvida.com', '0', 'SYSTEM', CURRENT_TIMESTAMP),
('EPS-015', 'Medimás (Liquidada)', '901.097.473-5', 'notificaciones@medimas.com.co', '0', 'SYSTEM', CURRENT_TIMESTAMP);
