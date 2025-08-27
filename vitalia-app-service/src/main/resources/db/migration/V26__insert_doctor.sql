
-- Inserta doctores que ya existen como PERSON
INSERT INTO DOCTOR (
    ID, LICENSE_NUMBER, IS_AVAILABLE, SPECIALTIES_SUMMARY, YEARS_OF_EXPERIENCE, RATING,
    FK_ID_USERPROFILE, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE
) VALUES (
    2, -- Debe coincidir con PERSON.ID
    'MED-12345',
    TRUE,
    'Medicina interna, Cardiología',
    12,
    4.5,
    null, -- FK a USER_PROFILE existente
    'admin',
    CURRENT_TIMESTAMP,
    'admin',
    CURRENT_TIMESTAMP
);