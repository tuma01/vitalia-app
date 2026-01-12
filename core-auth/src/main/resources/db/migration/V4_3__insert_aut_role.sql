 -- ***********************************************
 --  Insert AUT_ROLE
 -- ***********************************************
INSERT INTO AUT_ROLE (ID, NAME, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE)
VALUES
    (1, "ROLE_SUPER_ADMIN", "SYSTEM", CURRENT_TIMESTAMP, null, null),
    (2, "ROLE_ADMIN", "SYSTEM", CURRENT_TIMESTAMP, null, null),
    (3, "ROLE_DOCTOR", "SYSTEM", CURRENT_TIMESTAMP, null, null),
    (4, "ROLE_NURSE", "SYSTEM", CURRENT_TIMESTAMP, null, null),
    (5, "ROLE_PATIENT", "SYSTEM", CURRENT_TIMESTAMP, null, null),
    (6, "ROLE_EMPLOYEE", "SYSTEM", CURRENT_TIMESTAMP, null, null),
    (7, "ROLE_USER", "SYSTEM", CURRENT_TIMESTAMP, null, null),
    (8, "ROLE_GUEST", "SYSTEM", CURRENT_TIMESTAMP, null, null),
    (9, "ROLE_MODERATOR", "SYSTEM", CURRENT_TIMESTAMP, null, null),
    (10, "ROLE_CONTENT_CREATOR", "SYSTEM", CURRENT_TIMESTAMP, null, null),
    (11, "ROLE_CONTENT_EDITOR", "SYSTEM", CURRENT_TIMESTAMP, null, null),
    (12, "ROLE_SUPPORT", "SYSTEM", CURRENT_TIMESTAMP, null, null),
    (13, "ROLE_ANALYST", "SYSTEM", CURRENT_TIMESTAMP, null, null);

commit;