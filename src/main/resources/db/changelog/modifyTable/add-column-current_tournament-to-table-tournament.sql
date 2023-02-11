--liquibase formatted sql
--changeset liquibase:34c6b5b4-9858-4a4e-a8b4-876f53cc4331

ALTER TABLE tournament ADD COLUMN current_tournament TEXT AFTER RESULT_TOUR;

--rollback ALTER TABLE tournament DROP COLUMN current_tournament;