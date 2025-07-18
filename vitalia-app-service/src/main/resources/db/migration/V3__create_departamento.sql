DROP TABLE IF EXISTS DEPARTAMENTO;
CREATE TABLE DEPARTAMENTO (
    `ID` BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    `NOMBRE` VARCHAR(100) NOT NULL,
    `POBLACION` INT DEFAULT NULL,
    `SUPERFICIE` DECIMAL(38,2) DEFAULT NULL,
    `FK_ID_COUNTRY` BIGINT DEFAULT NULL,
    `CREATED_BY` VARCHAR(100) NOT NULL,
    `CREATED_DATE` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `LAST_MODIFIED_BY` VARCHAR(100) NOT NULL,
    `LAST_MODIFIED_DATE` TIMESTAMP NOT NULL,
    CONSTRAINT `FK_DEPARTAMENTO_COUNTRY` FOREIGN KEY (`FK_ID_COUNTRY`) REFERENCES `COUNTRY`(`ID`),
    UNIQUE KEY `UK_NOMBRE_DEPARTAMENTO` (`NOMBRE`)
);
COMMIT;
