-- ==============================================
-- INSERT SAMPLE DATA INTO ADDRESS
-- ==============================================
INSERT INTO address (ID, NUMERO, DIRECCION, BLOQUE, PISO, NUMERO_DEPARTAMENTO, MEDIDOR, CASILLA_POSTAL, CIUDAD, LOCATION, FK_ID_COUNTRY, FK_ID_DEPARTAMENTO) VALUES
(1, '123', 'Av. Principal', 'A', 3, '301', 'M001', 'CP100', 'Quito', 'POINT(-0.180653 -78.467834)', 1, 1),
(2, '456', 'Calle Secundaria', 'B', 5, '502', 'M002', 'CP101', 'Guayaquil', 'POINT(-2.189412 -79.889069)', 1, 2),
(3, '789', 'Av. Siempre Viva', NULL, 2, '201', 'M003', 'CP102', 'Cuenca', 'POINT(-2.90055 -79.00452)', 1, 3);
