--liquibase formatted sql
--changeset liquibase:65ed3160-fc37-4017-8f2b-6146ef538c85

create table IF NOT EXISTS tournament
(
    id                 bigint auto_increment
        primary key,
    title              varchar(255)                 not null,
    date               date                         not null,
    winner_id          bigint                       not null,
    address_id         bigint                       not null,
    amount_players     bigint                       not null,
    VERSION            int         default 0        not null,
    CREATED_BY         varchar(255)                 null,
    CREATED_DATE       timestamp                    null,
    LAST_MODIFIED_BY   varchar(255)                 null,
    LAST_MODIFIED_DATE timestamp                    null,
    STATUS             varchar(30) default 'ACTIVE' not null,
    RESULT_TOUR        varchar(2000)                null
)
    collate = utf8mb4_0900_ai_ci;




--rollback DROP TABLE tournament;