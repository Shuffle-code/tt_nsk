--liquibase formatted sql
--changeset liquibase:c1f6d282-5939-4d3f-b4f9-83cdf1d66c60

CREATE TABLE `address` (
                           `ID` bigint NOT NULL AUTO_INCREMENT,
                           `country` varchar(512) DEFAULT NULL,
                           `city` varchar(512) NOT NULL,
                           `street` varchar(512) NOT NULL,
                           `house` decimal(10,2) DEFAULT NULL,
                           `VERSION` int NOT NULL DEFAULT '0',
                           `CREATED_BY` varchar(255) DEFAULT NULL,
                           `CREATED_DATE` timestamp NULL DEFAULT NULL,
                           `LAST_MODIFIED_BY` varchar(255) DEFAULT NULL,
                           `LAST_MODIFIED_DATE` timestamp NULL DEFAULT NULL,
                           PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--rollback DROP TABLE address;