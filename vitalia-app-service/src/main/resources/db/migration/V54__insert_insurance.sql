INSERT INTO INSURANCE (
    INSURANCE_NUMBER,
    NAME,
    TELEPHONE,
    FAX,
    WEB_SITE,
    INSURANCE_DATE,
    EXPIRATION_DATE,
    FK_ID_ADDRESS,
    CONTACT_PERSON,
    CONTACT_EMAIL,
    POLICY_TYPE,
    POLICY_DETAILS
) VALUES (
    123456,
    'Seguro de vida Libertad',
    '555-1234',
    '555-5678',
    'https://segurolibertad.com',
    '2020-01-01',
    '2025-01-01',
    1,
    'Juan Perez',
    'juan.perez@example.com',
    'Vida Completa',
    'Cobertura total con deducible mínimo'
);
COMMIT;