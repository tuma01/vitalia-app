-- *******************************************************
-- Insert PERSON
-- Datos de prueba
-- *******************************************************

INSERT INTO PERSON
(ID, PERSON_TYPE, NOMBRE, SEGUNDO_NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, FECHA_NACIMIENTO, ESTADO_CIVIL, GENERO, TELEFONO, CELULAR, CREATED_BY, CREATED_DATE)
VALUES
(1, 'DOCTOR', 'Juan', 'Carlos', 'Amachi', 'Perez', '1980-05-10', 'CASADO', 'MASCULINO', '1234567', '987654321', 'system', CURRENT_TIMESTAMP),
(2, 'PATIENT', 'Ana', 'Maria', 'Lopez', 'Gomez', '2000-02-15', 'SOLTERO', 'FEMENINO', '7654321', '912345678', 'system', CURRENT_TIMESTAMP),
(3, 'NURSE', 'Luis', NULL, 'Ramirez', 'Torres', '1990-08-20', 'SOLTERO', 'MASCULINO', '1112222', '999888777', 'system', CURRENT_TIMESTAMP);
