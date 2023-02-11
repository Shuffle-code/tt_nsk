--liquibase formatted sql
--changeset liquibase:065240cf-8a8c-4d76-ab17-ab8031dcb980

ALTER TABLE player MODIFY rating decimal(10, 2) default 500.00 not null;

--rollback ALTER TABLE player MODIFY rating decimal(10, 2) default 500.00 null;