-- ==============================================
-- INSERT SAMPLE DATA INTO GEO_ADDRESS
-- ==============================================
INSERT INTO GEO_ADDRESS (ID, NUMERO, DIRECCION, BLOQUE, PISO, NUMERO_DEPARTAMENTO, MEDIDOR, CASILLA_POSTAL, CIUDAD, LOCATION, FK_ID_COUNTRY, FK_ID_DEPARTAMENTO) VALUES
(1, '123', 'Av. 16 de Julio', 'A', 3, '301', 'M001', 'CP100', 'La Paz', 'POINT(-16.5000 -68.1500)', 26, 1),
(2, '456', 'Av. Heroinas', 'B', 5, '502', 'M002', 'CP101', 'Cochabamba', 'POINT(-17.3895 -66.1568)', 26, 2),
(3, '789', 'Av. Banzer', NULL, 2, '201', 'M003', 'CP102', 'Santa Cruz', 'POINT(-17.7833 -63.1821)', 26, 3);
