-- Inserción de datos en la tabla Municipalidad


INSERT INTO Municipio (id, nombre, direccion, codigo_municipio, poblacion, superficie, FK_ID_PROVINCIA, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE) VALUES
(1, 'Trinidad', 'Calle Pedro de la Rocha esq. La Paz', 080101, 106596, 0, 1, "admin", CURRENT_TIMESTAMP, "admin", CURRENT_TIMESTAMP),
(2, 'San Javier', '', 080102, 5277, 0, 1, "admin", CURRENT_TIMESTAMP, "admin", CURRENT_TIMESTAMP),
(3, 'Riberalta', '', 080201, 89022, 0, 2, "admin", CURRENT_TIMESTAMP, "admin", CURRENT_TIMESTAMP),
(4, 'Guayaramerin', '', 080202, 41814, 0, 2, "admin", CURRENT_TIMESTAMP, "admin", CURRENT_TIMESTAMP),
(5, 'Reyes', 'Calle 18 de Noviembre Acera Norte, Pza Jorge Guardia', 080301, 13246, 0, 3, "admin", CURRENT_TIMESTAMP, "admin", CURRENT_TIMESTAMP),
(6, 'San Borja', '', 080302, 40864, 0, 3, "admin", CURRENT_TIMESTAMP, "admin", CURRENT_TIMESTAMP),
(7, 'Santa Rosa', '', 080303, 9478, 0, 3, "admin", CURRENT_TIMESTAMP, "admin", CURRENT_TIMESTAMP),
(8, 'Rurrenabaque', 'Plaza 2 de febrero', 080304, 19195, 0, 3, "admin", CURRENT_TIMESTAMP, "admin", CURRENT_TIMESTAMP),
(9, 'Santa Ana de Yacuma', 'Rua Ribeirão Pires, Casa 199 Itaquaquecetuba', 080401, 18439, 0, 4, "admin", CURRENT_TIMESTAMP, "admin", CURRENT_TIMESTAMP),
(10, 'Exaltacion', '', 080402,6362, 0, 4, "admin", CURRENT_TIMESTAMP, "admin", CURRENT_TIMESTAMP),
(11, 'San Ignacio', '', 080501, 21114, 0, 5, "admin", CURRENT_TIMESTAMP, "admin", CURRENT_TIMESTAMP),
(12, 'Loreto', '', 080601, 3828, 0, 6, "admin", CURRENT_TIMESTAMP, "admin", CURRENT_TIMESTAMP),
(13, 'San Andres', '', 080602, 12503, 0, 6, "admin", CURRENT_TIMESTAMP, "admin", CURRENT_TIMESTAMP),
(14, 'San Joaquin', '', 080701, 6917, 0, 7, "admin", CURRENT_TIMESTAMP, "admin", CURRENT_TIMESTAMP),
(15, 'San Ramon', 'Calle 9, Pando', 080702, 4955, 0, 7, "admin", CURRENT_TIMESTAMP, "admin", CURRENT_TIMESTAMP),
(16, 'Puerto Siles', 'Calle 9, Pando', 080703, 945, 0, 7, "admin", CURRENT_TIMESTAMP, "admin", CURRENT_TIMESTAMP),
(17, 'Magdalena', 'Calle 9, Pando', 080801, 11377, 0, 8, "admin", CURRENT_TIMESTAMP, "admin", CURRENT_TIMESTAMP),
(18, 'Baures', 'Calle 9, Pando', 080802, 5965, 0, 8, "admin", CURRENT_TIMESTAMP, "admin", CURRENT_TIMESTAMP),
(19, 'Huacaraje', 'Calle 9, Pando', 080803, 4111, 0, 8, "admin", CURRENT_TIMESTAMP, "admin", CURRENT_TIMESTAMP);
commit;