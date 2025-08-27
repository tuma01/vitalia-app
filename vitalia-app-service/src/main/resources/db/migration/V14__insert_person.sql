-- ***********************************************
--   Insert data in PERSON
-- ***********************************************
INSERT INTO PERSON (ID, PERSON_TYPE, NOMBRE, SEGUNDO_NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, FECHA_NACIMIENTO, ESTADO_CIVIL, GENERO, FK_ID_ADDRESS, TELEFONO, CELULAR, FK_ID_DEFAULT_HOSPITAL, CREATED_BY, CREATED_DATE)
VALUES
(1, 'NURSE', 'Maria', 'Teodora', 'Castaneda', 'Fierro', '2011-04-01', 'SOLTERO', 'FEMENINO', 1, '5142367944', '', 1, 'system', CURRENT_TIMESTAMP),
(2, 'DOCTOR', 'Roly', 'Jose', 'Jamachi', 'Mamani', '2008-12-24', 'SOLTERO', 'MASCULINO', 3, '5142367944', '', 1, 'system', CURRENT_TIMESTAMP);

commit;

--(2, 'Roly', '', 'Jamachi', 'Mamani', '2008-12-24', 'SOLTERO', 'MASCULINO', 3, 'DOCTOR', '5142367944', '', 'system', CURRENT_TIMESTAMP);