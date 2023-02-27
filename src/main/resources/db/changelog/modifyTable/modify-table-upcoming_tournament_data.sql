--liquibase formatted sql
--changeset liquibase:7a7f3ff7-2743-42d0-8dbb-79c7525b230e

ALTER TABLE upcoming_tournament_data MODIFY tour_id bigint not null unique;

--rollback ALTER TABLE upcoming_tournament_data MODIFY tour_id bigint    not null;