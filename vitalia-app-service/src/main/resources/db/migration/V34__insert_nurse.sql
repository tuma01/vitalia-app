-- Insertar en NURSE
INSERT INTO NURSE (
    ID, PHOTO, ID_CARD, FK_ID_USERPROFILE, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE
) VALUES (
    1,
    NULL, -- suponiendo que no insertamos foto por ahora
    'NC-123456',
    1,
    'system',
    CURRENT_TIMESTAMP,
    'system',
    CURRENT_TIMESTAMP
);
commit;