--liquibase formatted sql
--changeset liquibase:26809685-634f-43e0-a2c3-ce933b78c8a1

CREATE TABLE IF NOT EXISTS `player_image` (
                                `ID` bigint NOT NULL AUTO_INCREMENT,
                                `path` varchar(512) NOT NULL,
                                `player_id` bigint DEFAULT NULL,
                                PRIMARY KEY (`ID`),
                                KEY `FK_PLAYER_IMAGE_PLAYER` (`player_id`),
                                CONSTRAINT `FK_PLAYER_IMAGE_PLAYER` FOREIGN KEY (`player_id`) REFERENCES `player` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=109 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--rollback DROP TABLE `player_image`;