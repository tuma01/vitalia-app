-- ***********************************************
--   Insert data in PERSON
-- ***********************************************
INSERT INTO PERSON (ID, NOMBRE, SEGUNDO_NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, FECHA_NACIMIENTO, ESTADO_CIVIL, GENERO, FK_ID_ADDRESS, person_type, TELEFONO, CELULAR, CREATED_BY, CREATED_DATE)
VALUES
(1, 'Maria', 'Teodora', 'Castaneda', 'Fierro', '2011-04-01', 'SOLTERO', 'FEMENINO', 1,  'NURSE', '5142367944', '', 'system', CURRENT_TIMESTAMP);

commit;

--(2, 'Roly', '', 'Jamachi', 'Mamani', '2008-12-24', 'SOLTERO', 'MASCULINO', 3, 'DOCTOR', '5142367944', '', 'system', CURRENT_TIMESTAMP);