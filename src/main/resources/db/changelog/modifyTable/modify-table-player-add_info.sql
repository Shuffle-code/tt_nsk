--liquibase formatted sql
--changeset liquibase:31eaef3b-c09c-40e3-a94a-685efeef982a

ALTER TABLE player ADD COLUMN info TEXT;

--rollback ALTER TABLE tournament DROP COLUMN current_tournament;