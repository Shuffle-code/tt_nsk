--liquibase formatted sql
--changeset liquibase:2a3a3ad3-7e9a-4c67-9642-27df69f8a7a7

CREATE TABLE IF NOT EXISTS `role_authority` (
                                  `ROLE_ID` bigint NOT NULL,
                                  `AUTHORITY_ID` bigint NOT NULL,
                                  PRIMARY KEY (`ROLE_ID`,`AUTHORITY_ID`),
                                  KEY `FK_ROLE_AUTHORITY_AUTHORITY` (`AUTHORITY_ID`),
                                  CONSTRAINT `FK_ROLE_AUTHORITY_ACCOUNT_ROLE` FOREIGN KEY (`ROLE_ID`) REFERENCES `account_role` (`ID`),
                                  CONSTRAINT `FK_ROLE_AUTHORITY_AUTHORITY` FOREIGN KEY (`AUTHORITY_ID`) REFERENCES `authority` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;


--rollback DROP TABLE `role_authority` ;