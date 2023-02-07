--liquibase formatted sql
--changeset liquibase:283fbf3f-4cf0-4f25-98e8-f9b88a7b1d0e

CREATE TABLE `user_role` (
                             `USER_ID` bigint NOT NULL,
                             `ROLE_ID` bigint NOT NULL,
                             PRIMARY KEY (`USER_ID`,`ROLE_ID`),
                             KEY `FK_USER_ROLE_ACCOUNT_ROLE` (`ROLE_ID`),
                             CONSTRAINT `FK_USER_ROLE_ACCOUNT_ROLE` FOREIGN KEY (`ROLE_ID`) REFERENCES `account_role` (`ID`),
                             CONSTRAINT `FK_USER_ROLE_ACCOUNT_USER` FOREIGN KEY (`USER_ID`) REFERENCES `account_user` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--rollback DROP TABLE `user_role`;