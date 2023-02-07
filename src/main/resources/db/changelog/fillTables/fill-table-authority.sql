--liquibase formatted sql
--changeset liquibase:ba2eb20a-125d-4ed1-933d-676952c4c932

INSERT INTO `authority`
VALUES (1, 'player.create'),
       (4, 'player.delete'),
       (6, 'player.participant'),
       (5, 'player.player'),
       (3, 'player.read'),
       (7, 'player.readOnly'),
       (2, 'player.update');

--rollback DELETE FROM `authority` WHERE id BETWEEN 1 AND 7;