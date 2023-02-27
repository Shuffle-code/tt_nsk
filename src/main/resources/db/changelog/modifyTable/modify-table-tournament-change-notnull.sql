--liquibase formatted sql
--changeset liquibase:31eaef3b-c09c-40e3-a94a-685efeef982a

ALTER TABLE `nsk_tt`.`tournament`
    CHANGE COLUMN `winner_id` `winner_id` BIGINT NULL ,
    CHANGE COLUMN `amount_players` `amount_players` BIGINT NULL ;
--rollback ALTER TABLE tournament DROP COLUMN current_tournament;