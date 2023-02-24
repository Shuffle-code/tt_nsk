-- liquibase formatted sql
-- changeset liquibase:c3060fe5-7799-49e5-b314-a999e0549f10 splitStatements:false
-- delimiter $$

CREATE TRIGGER  reg_expired
    BEFORE INSERT
    ON registered_players FOR EACH ROW
BEGIN
    IF ((SELECT reg_ends FROM upcoming_tournament_data) < CURRENT_DATE) THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Регистрация на турнир уже закончилась';

    END IF;
END
-- $$
-- delimiter ;

--DROP TRIGGER IF EXISTS reg_expired;