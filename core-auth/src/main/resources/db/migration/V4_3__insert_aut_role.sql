 -- ***********************************************
 --  Insert AUT_ROLE
 -- ***********************************************
INSERT INTO AUT_ROLE (ID, NAME, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE)
VALUES
    (1, "ROLE_SUPER_ADMIN", "system", CURRENT_TIMESTAMP, null, null),
    (2, "ROLE_ADMIN", "system", CURRENT_TIMESTAMP, null, null),
    (3, "ROLE_DOCTOR", "system", CURRENT_TIMESTAMP, null, null),
    (4, "ROLE_NURSE", "system", CURRENT_TIMESTAMP, null, null),
    (5, "ROLE_PATIENT", "system", CURRENT_TIMESTAMP, null, null),
    (6, "ROLE_EMPLOYEE", "system", CURRENT_TIMESTAMP, null, null),
    (7, "ROLE_USER", "system", CURRENT_TIMESTAMP, null, null),
    (8, "ROLE_GUEST", "system", CURRENT_TIMESTAMP, null, null),
    (9, "ROLE_MODERATOR", "system", CURRENT_TIMESTAMP, null, null),
    (10, "ROLE_CONTENT_CREATOR", "system", CURRENT_TIMESTAMP, null, null),
    (11, "ROLE_CONTENT_EDITOR", "system", CURRENT_TIMESTAMP, null, null),
    (12, "ROLE_SUPPORT", "system", CURRENT_TIMESTAMP, null, null),
    (13, "ROLE_ANALYST", "system", CURRENT_TIMESTAMP, null, null);

commit;