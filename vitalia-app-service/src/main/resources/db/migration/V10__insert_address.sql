-- ***********************************************
--   Insert data in ADDRESS
-- ***********************************************
INSERT INTO ADDRESS (ID, NUMERO, DIRECCION, BLOQUE, PISO, NUMERO_DEPARTAMENTO, MEDIDOR, CASILLA_POSTAL, CIUDAD, LOCATION, FK_ID_COUNTRY, FK_ID_DEPARTAMENTO) VALUES
    (1, '11957', 'Avenue Jubinville', '', 3, '301', '', 'H1G 3T3', 'La Paz', '', 26, 1),
    (2, '123', 'Jean Talon', '', 1, '3A', '', 'H2G 4T4', 'Montreal', '', 26, 4),
    (3, '128', 'Boulevard Gouin', '', 0, '', '', 'H2G 4T4', 'Montreal', '', 26, 2),
    (4, '123', 'Cote Vertu', '', 1, '3A', '', 'H2G 4T1', 'Laval', '', 26, 3),
    (5, '123', 'Camacho', '', 0, '', '', 'H2G 4T1', 'La Paz', '', 26, 3);
commit;