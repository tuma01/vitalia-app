-- ***********************************************
--   Insert data in PERSON
-- ***********************************************
INSERT INTO PERSON (ID, NOMBRE, SEGUNDO_NOMBRE, APELLIDO_PATERNO, NOMBRE_COMPLETO, APELLIDO_MATERNO, DIA_NACIMIENTO, MES_NACIMIENTO, ANO_NACIMIENTO, FECHA_NACIMIENTO, ESTADO_CIVIL, GENERO, FK_ID_ADDRESS, person_type, TELEFONO, CELULAR, CREATED_BY, CREATED_DATE)
VALUES
(1, 'Nurse One', 'Teodora', 'Castaneda', 'Fierro', 'Nurse One, Castaneda', 24, 01, 2011,  '2011-04-01', 'SOLTERO', 'FEMENINO', 1, 'NURSE', '5142367944', '', 'system', CURRENT_TIMESTAMP),
(2, 'Roly', '', 'Jamachi', 'Perez', 'Roly Jamachi', 8, 8, 2008, '2008-12-24', 'SOLTERO', 'MASCULINO', 3, 'DOCTOR', '5142367944', '', 'system', CURRENT_TIMESTAMP);
commit;