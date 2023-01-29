--liquibase formatted sql
--changeset liquibase:e8015624-a929-4936-a74c-ea1c3ebd9144

INSERT INTO `address` VALUES (1,'РФ','Иркутск','Ленина',5.00,0,NULL,NULL,NULL,NULL);

--rollback DELETE FROM `address` WHERE id = 1;