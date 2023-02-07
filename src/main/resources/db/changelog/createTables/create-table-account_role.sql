--liquibase formatted sql
--changeset liquibase:a45f96e9-6856-4859-9c4b-6eeaedba682f

CREATE TABLE IF NOT EXISTS `account_role` (
                                `ID` bigint NOT NULL AUTO_INCREMENT,
                                `name` varchar(255) NOT NULL,
                                PRIMARY KEY (`ID`),
                                UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
ALTER TABLE `account_role` DISABLE KEYS;
INSERT INTO `account_role` VALUES (1,'ROLE_ADMIN'),(4,'ROLE_GUEST'),(3,'ROLE_PLAYER'),(2,'ROLE_USER');
ALTER TABLE `account_role` ENABLE KEYS;

--rollback DROP TABLE account_role;

