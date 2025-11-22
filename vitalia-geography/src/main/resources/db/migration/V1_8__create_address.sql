-- ==============================================
-- TABLE: ADDRESS
-- ==============================================
DROP TABLE IF EXISTS ADDRESS;
CREATE TABLE IF NOT EXISTS `ADDRESS` (
    ID BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    NUMERO VARCHAR(100) comment 'The first line of the address.',
    DIRECCION VARCHAR(100) comment 'The second line of address. It is Optional.',
    BLOQUE VARCHAR(50),
    PISO INT,
    NUMERO_DEPARTAMENTO VARCHAR(20),
    MEDIDOR VARCHAR(20),
    CASILLA_POSTAL VARCHAR(20) comment 'The postal code of the address.',
    CIUDAD VARCHAR(100) comment 'The name City of the address.',
    LOCATION VARCHAR(100) comment 'Geometry data with spatial index.',
    FK_ID_COUNTRY BIGINT,
    FK_ID_DEPARTAMENTO BIGINT,
    CONSTRAINT FK_ADDRESS_COUNTRY FOREIGN KEY (FK_ID_COUNTRY) REFERENCES COUNTRY(ID),
    CONSTRAINT FK_ADDRESS_DEPARTAMENTO FOREIGN KEY (FK_ID_DEPARTAMENTO) REFERENCES DEPARTAMENTO(ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
commit;





