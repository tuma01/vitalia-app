-- ***********************************************
-- Insert USER (Datos de prueba)
-- ***********************************************

INSERT INTO USER (ID, EMAIL, PASSWORD, ACCOUNT_LOCKED, ENABLED, CREATED_BY, CREATED_DATE)
VALUES
(1, 'admin@example.com', 'admin123', FALSE, TRUE, 'system', CURRENT_TIMESTAMP),
(2, 'doctor@example.com', 'doctor123', FALSE, TRUE, 'system', CURRENT_TIMESTAMP),
(3, 'nurse@example.com', 'nurse123', FALSE, TRUE, 'system', CURRENT_TIMESTAMP),
(4, 'patient@example.com', 'patient123', FALSE, TRUE, 'system', CURRENT_TIMESTAMP);




