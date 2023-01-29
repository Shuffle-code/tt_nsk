--liquibase formatted sql
--changeset liquibase:8dca3123-c547-4e4d-b382-bb0195fb4ad8

CREATE TABLE IF NOT EXISTS `player_ttw` (
                              `id` varchar(255) NOT NULL,
                              `name` varchar(255) NOT NULL,
                              `rating_ttw` decimal(10,2) DEFAULT NULL,
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--rollback DROP TABLE `player_ttw`;