-- liquibase formatted sql
-- changeset liquibase:0c98857a-342d-4ff6-92c5-4fd2b2347dc0 splitStatements:false
-- delimiter $$

CREATE TRIGGER  reg_status
    BEFORE INSERT
    ON registered_players FOR EACH ROW
BEGIN
    IF ((SELECT COUNT(*) FROM registered_players WHERE tour_id = NEW.tour_id)
        > (SELECT total_players FROM nsk_tt.upcoming_tournament_data WHERE tour_id = NEW.tour_id) - 1) THEN
        SET NEW.status = 'RESERVED';
    ELSE SET NEW.status = 'REGISTERED';

    END IF;
END-- $$
-- delimiter ;

--DROP TRIGGER IF EXISTS reg_status;