-- ==============================================
-- V30__insert_user_profile_sample_data.sql
-- Insert de prueba para USER_PROFILE y entidades hijas
-- ==============================================

-- Insertar un UserProfile de prueba
INSERT INTO USER_PROFILE (BIOGRAPHY, PHOTO)
VALUES ('Test user biography for profile.', NULL);

-- Insertar una Education relacionada
INSERT INTO EDUCATION (INSTITUTION, DEGREE, START_DATE, END_DATE, FK_ID_USERPROFILE)
VALUES ('Harvard University', 'MD in Medicine', '2010-09-01', '2014-06-30', 1);

-- Insertar una Experience relacionada
INSERT INTO EXPERIENCE (TITLE, INSTITUTION, START_DATE, END_DATE, DESCRIPTION, FK_ID_USERPROFILE)
VALUES ('General Practitioner', 'City Hospital', '2015-01-01', '2020-12-31', 'Primary care diagnostics', 1);

-- Insertar un Course relacionado
INSERT INTO COURSE (TITLE, INSTITUTION, DESCRIPTION, DATE, DURATION_IN_HOURS, CERTIFICATE, FK_ID_USERPROFILE)
VALUES ('Advanced Cardiology', 'Medical Institute', 'Specialization in cardiology', '2019-05-20', 40, 'cert_cardio_2019.pdf', 1);

-- Insertar una Conference relacionada
INSERT INTO CONFERENCE (TOPIC, DESCRIPTION, ORGANIZER, LOCATION, IS_INTERNATIONAL, DATE, FK_ID_USERPROFILE)
VALUES ('Global Health Summit', 'Annual health innovation event', 'WHO', 'Geneva', 1, '2022-11-10', 1);

-- Confirmar cambios
COMMIT;