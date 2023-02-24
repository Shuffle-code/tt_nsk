--liquibase formatted sql
--changeset liquibase:e1a6b89f-bfc5-4501-a466-eda7b1b9ac11

create table IF NOT EXISTS upcoming_tournament_data
(
    ID   bigint auto_increment
    primary key,
    tour_id bigint not null,
    total_players smallint not null,
    reg_ends timestamp not null,
    constraint FK_TOUR_ID
    foreign key (tour_id) references tournament (id)
    )
    collate = utf8mb4_0900_ai_ci;

--rollback DROP TABLE upcoming_tournament_data;