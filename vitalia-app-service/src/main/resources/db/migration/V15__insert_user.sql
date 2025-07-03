 -- ***********************************************
 --  Insert USER
 -- ***********************************************
INSERT INTO USER (ID, EMAIL, PASSWORD, ACCOUNT_LOCKED, ENABLED, FK_ID_PERSON, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE)
VALUES
    (1, "nurse@gmail.com", "$10$AJwCUAhlw.PbeNX5GHK.ZOEAlnGmHua5q/Qf6Sycq/PThOvd1vvN.", FALSE, TRUE, 1, "system", CURRENT_TIMESTAMP, null, null);
--    (2, "doctor@gmail.com", "$10$AJwCUAhlw.PbeNX5GHK.ZOEAlnGmHua5q/Qf6Sycq/PThOvd1vvN.", FALSE, TRUE, 2, "system", CURRENT_TIMESTAMP, null, null);

commit;