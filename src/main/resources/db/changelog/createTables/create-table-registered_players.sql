--liquibase formatted sql
--changeset liquibase:2ace4c64-d292-411e-943c-8d4213d91e27

CREATE TABLE registered_players (
                                    ID bigint auto_increment primary key,
                                    player_id bigint not null,
                                    tour_id bigint not null,
                                    status ENUM('registered', 'reserved') not null,
                                    CONSTRAINT PLAYER_ID foreign key (player_id) references player(id) ON DELETE CASCADE,
                                    CONSTRAINT TOUR_ID foreign key (tour_id) references upcoming_tournament_data(tour_id) ON DELETE CASCADE,
                                    CONSTRAINT UNIQUE (player_id, tour_id)

);

--rollback DROP TABLE registered_players;