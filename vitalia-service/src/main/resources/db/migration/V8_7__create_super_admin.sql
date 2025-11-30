-- *******************************************************
-- Script de creación de tabla SUPER_ADMIN
-- *******************************************************

DROP TABLE IF EXISTS SUPER_ADMIN;

-- SUPER_ADMIN table DDL (MySQL)
CREATE TABLE SUPER_ADMIN (
    ID BIGINT PRIMARY KEY NOT NULL,
    LEVEL ENUM('LEVEL_1','LEVEL_2','LEVEL_3') NOT NULL,
    GLOBAL_ACCESS BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT FK_SUPERADMIN_PERSON FOREIGN KEY (ID) REFERENCES PERSON(ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
