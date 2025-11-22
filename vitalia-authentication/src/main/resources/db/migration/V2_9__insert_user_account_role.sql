 -- ***********************************************
 --  Insert USER_ACCOUNT_ROLE
 -- ***********************************************
-- Suponiendo que los roles ya existen con IDs 1 = ROLE_DOCTOR, 2 = ROLE_PATIENT, 3 = ROLE_NURSE
INSERT INTO USER_ACCOUNT_ROLE (USER_ACCOUNT_ID, ROLE_ID)
VALUES
  (1, 1), -- Doctor
  (2, 2), -- Patient
  (3, 3); -- Nurse