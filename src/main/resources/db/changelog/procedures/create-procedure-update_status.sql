-- liquibase formatted sql
-- changeset liquibase:a67500b5-0b7c-4141-92fd-e4c46da1213a splitStatements:false
-- delimiter $$

CREATE PROCEDURE update_status(IN tourid_prm BIGINT)
BEGIN
    IF (SELECT COUNT(*) FROM registered_players WHERE status = 'REGISTERED' <
                                                      (SELECT total_players FROM upcoming_tournament_data WHERE tour_id = tourid_prm)) THEN
        UPDATE registered_players ra
        SET ra.status = 'REGISTERED'
        WHERE ID IN (SELECT * FROM (SELECT ID FROM registered_players  WHERE status = 'RESERVED' ORDER BY ID DESC LIMIT 1) AS rp);
    END IF;
END;

-- $$
-- delimiter ;

--DROP PROCEDURE update_status;

