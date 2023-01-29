--liquibase formatted sql
--changeset liquibase:b48c7f29-87a2-4a12-a895-ab4ade77dd90

create table IF NOT EXISTS player
(
    id                 bigint auto_increment
        primary key,
    firstname          varchar(255)                    not null,
    patronymic         varchar(255)                    null,
    lastname           varchar(255)                    null,
    rating             decimal(10, 2) default 500.00   null,
    rating_ttw         decimal(10, 2)                  null,
    year_of_birth      int                             null,
    VERSION            int            default 0        null,
    CREATED_BY         varchar(255)                    null,
    CREATED_DATE       timestamp                       null,
    LAST_MODIFIED_BY   varchar(255)                    null,
    LAST_MODIFIED_DATE timestamp                       null,
    STATUS             varchar(30)    default 'ACTIVE' not null,
    TOUR_ID            bigint                          null,
    ID_TTWR            varchar(255)                    null
)
    collate = utf8mb4_0900_ai_ci;

--rollback DROP TABLE player;