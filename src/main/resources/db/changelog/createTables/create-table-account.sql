--liquibase formatted sql
--changeset liquibase:f3d86be1-f8b0-4f87-b2de-28f842441670

create table IF NOT EXISTS account_role
(
    ID   bigint auto_increment
        primary key,
    name varchar(255) not null,
    constraint name
        unique (name)
)
    collate = utf8mb4_0900_ai_ci;

--rollback DROP TABLE account_role;