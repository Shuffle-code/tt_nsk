--liquibase formatted sql
--changeset liquibase:44ec8941-9a5e-473f-9173-550969d72d2c

INSERT INTO `account_role` VALUES (1,'ROLE_ADMIN'),(4,'ROLE_GUEST'),(3,'ROLE_PLAYER'),(2,'ROLE_USER');

--rollback DELETE FROM account_role WHERE id = 1;