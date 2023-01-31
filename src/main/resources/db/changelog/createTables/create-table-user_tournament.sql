--liquibase formatted sql
--changeset liquibase:3b8904fd-59e4-4705-9037-96bbb619fc78

create table if not exists `player_tournament`
(
    id            bigint auto_increment primary key,
    player_id     bigint not null,
    tournament_id bigint not null,
    constraint FK_PLAYER
        foreign key (player_id) references player (id),
    constraint FK_TOURNAMENT
        foreign key (tournament_id) references tournament (id),
    CONSTRAINT uniqueLink UNIQUE (player_id, tournament_id)
)
    collate = utf8mb4_0900_ai_ci;

--rollback DROP TABLE `player_tournament`;