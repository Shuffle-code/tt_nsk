--liquibase formatted sql
--changeset liquibase:1a5a65c0-a995-4653-9de0-80dc2ccec431

CREATE TABLE IF NOT EXISTS `authority` (
                             `ID` bigint NOT NULL AUTO_INCREMENT,
                             `permission` varchar(255) NOT NULL,
                             PRIMARY KEY (`ID`),
                             UNIQUE KEY `permission` (`permission`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--rollback DROP TABLE `authority`;