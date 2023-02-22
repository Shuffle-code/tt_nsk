-- liquibase formatted sql
-- changeset liquibase:c2ef62f8-f1f8-4dc7-a6f6-b75e8d2992b4 splitStatements:false
-- delimiter $$

CREATE TRIGGER  next_day
    BEFORE INSERT
    ON upcoming_tournament_data FOR EACH ROW
BEGIN
    IF NEW.reg_ends <= (CURRENT_DATE + INTERVAL 1 DAY) THEN
        SIGNAL SQLSTATE '45000'
      SET MESSAGE_TEXT = 'Регистрация должна заканчиваться не позднее дня создания турнира';

    END IF;
END-- $$
-- delimiter ;

--DROP TRIGGER IF EXISTS next_day;

