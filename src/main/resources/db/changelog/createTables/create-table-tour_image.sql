--liquibase formatted sql
--changeset liquibase:ea440c5f-3287-4e9d-9ae8-038a403ec034

CREATE TABLE IF NOT EXISTS `tour_image` (
                              `ID` bigint NOT NULL AUTO_INCREMENT,
                              `path` varchar(512) NOT NULL,
                              `tour_id` bigint DEFAULT NULL,
                              PRIMARY KEY (`ID`),
                              KEY `FK_TOUR_IMAGE_TOURNAMENT` (`tour_id`),
                              CONSTRAINT `FK_TOUR_IMAGE_TOURNAMENT` FOREIGN KEY (`tour_id`) REFERENCES `tournament` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=110 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;




--rollback DROP TABLE `tour_image`;