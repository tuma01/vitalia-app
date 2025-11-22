 -- ***********************************************
 --  Insert USER_ACCOUNT
 -- ***********************************************
INSERT INTO USER_ACCOUNT (ID, USER_ID, PERSON_ID, TENANT_ID, CREATED_BY, CREATED_DATE)
VALUES
  (1, 1, 1, 1, 'system', CURRENT_TIMESTAMP),
  (2, 2, 2, 1, 'system', CURRENT_TIMESTAMP),
  (3, 3, 3, 2, 'system', CURRENT_TIMESTAMP);