
SET @USER_ID = 3;
SET @ROLE_ID = 1;
SET @PERSON_ID = 3;

DELETE FROM `hospital_db`.`user_roles`
WHERE USER_ID = @USER_ID
AND ROLE_ID = @ROLE_ID;

DELETE FROM `hospital_db`.`token`
WHERE FK_ID_USER = @USER_ID;

DELETE FROM `hospital_db`.`user`
WHERE ID = @USER_ID;


DELETE FROM `hospital_db`.`person`
WHERE ID = @PERSON_ID;
commit;