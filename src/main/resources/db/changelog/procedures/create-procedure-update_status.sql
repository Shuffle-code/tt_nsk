-- liquibase formatted sql
-- changeset liquibase:a67500b5-0b7c-4141-92fd-e4c46da1213a splitStatements:false
-- delimiter $$

CREATE PROCEDURE update_status(IN tourid BIGINT, IN maxplayers SMALLINT)
BEGIN

    DROP TEMPORARY TABLE IF EXISTS mw;
    CREATE TEMPORARY TABLE mw (max_players smallint);
    INSERT INTO mw SELECT ID FROM registered_players WHERE tour_id = tourid ORDER BY ID ASC LIMIT maxplayers;
    UPDATE registered_players rp2 SET status = 'RESERVED' WHERE tour_id = tourid AND ID NOT IN (SELECT * FROM mw AS mw1);
    UPDATE registered_players rp2 SET status = 'REGISTERED' WHERE tour_id = tourid AND ID IN (SELECT * FROM mw AS mw1);
    DROP TEMPORARY TABLE IF EXISTS mw;
END;

-- $$
-- delimiter ;

--DROP PROCEDURE update_status;

