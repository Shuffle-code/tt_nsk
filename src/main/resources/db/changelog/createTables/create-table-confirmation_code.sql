--liquibase formatted sql
--changeset liquibase:e2ccf1f3-5f58-4595-ad51-9ef3000b92fa

CREATE TABLE IF NOT EXISTS `confirmation_code` (
                                     `ID` bigint NOT NULL AUTO_INCREMENT,
                                     `code` varchar(255) NOT NULL,
                                     `USER_ID` bigint DEFAULT NULL,
                                     PRIMARY KEY (`ID`),
                                     UNIQUE KEY `code` (`code`),
                                     KEY `FK_ACCOUNT_USER` (`USER_ID`),
                                     CONSTRAINT `FK_ACCOUNT_USER` FOREIGN KEY (`USER_ID`) REFERENCES `account_user` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--rollback DROP TABLE `confirmation_code`;