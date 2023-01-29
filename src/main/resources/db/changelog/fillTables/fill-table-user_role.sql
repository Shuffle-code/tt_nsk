--liquibase formatted sql
--changeset liquibase:df4b2be7-4cb1-4a36-a19c-e05e9ed4fe89

INSERT INTO `user_role`
VALUES (1, 1),
       (3, 2),
       (4, 2),
       (5, 2),
       (6, 2),
       (7, 2),
       (9, 2),
       (11, 2),
       (17, 2),
       (18, 2),
       (21, 2),
       (25, 2),
       (29, 2),
       (30, 2),
       (32, 2),
       (33, 2),
       (36, 2),
       (2, 3),
       (8, 3),
       (10, 3),
       (12, 3),
       (13, 3),
       (14, 3),
       (15, 3),
       (16, 3),
       (19, 3),
       (20, 3),
       (22, 3),
       (23, 3),
       (24, 3),
       (26, 3),
       (27, 3),
       (28, 3),
       (31, 3),
       (34, 3),
       (35, 3);

--rollback DELETE FROM `user_role` WHERE
    (USER_ID = 1 AND ROLE_ID = 1) OR
    (USER_ID = 3 AND ROLE_ID = 2) OR
    (USER_ID = 4 AND ROLE_ID = 2) OR
    (USER_ID = 5 AND ROLE_ID = 2) OR
    (USER_ID = 6 AND ROLE_ID = 2) OR
    (USER_ID = 7 AND ROLE_ID = 2) OR
    (USER_ID = 11 AND ROLE_ID = 2) OR
    (USER_ID = 17 AND ROLE_ID = 2) OR
    (USER_ID = 18 AND ROLE_ID = 2) OR
    (USER_ID = 21 AND ROLE_ID = 2) OR
    (USER_ID = 25 AND ROLE_ID = 2) OR
    (USER_ID = 29 AND ROLE_ID = 2) OR
    (USER_ID = 30 AND ROLE_ID = 2) OR
    (USER_ID = 32 AND ROLE_ID = 2) OR
    (USER_ID = 33 AND ROLE_ID = 2) OR
    (USER_ID = 36 AND ROLE_ID = 2) OR
    (USER_ID = 2 AND ROLE_ID = 3) OR
    (USER_ID = 8 AND ROLE_ID = 3) OR
    (USER_ID = 10 AND ROLE_ID = 2) OR
    (USER_ID = 12 AND ROLE_ID = 2) OR
    (USER_ID = 13 AND ROLE_ID = 3) OR
    (USER_ID = 14 AND ROLE_ID = 3) OR
    (USER_ID = 15 AND ROLE_ID = 3) OR
    (USER_ID = 16 AND ROLE_ID = 3) OR
    (USER_ID = 19 AND ROLE_ID = 3) OR
    (USER_ID = 20 AND ROLE_ID = 3) OR
    (USER_ID = 22 AND ROLE_ID = 3) OR
    (USER_ID = 23 AND ROLE_ID = 3) OR
    (USER_ID = 24 AND ROLE_ID = 3) OR
    (USER_ID = 26 AND ROLE_ID = 3) OR
    (USER_ID = 27 AND ROLE_ID = 3) OR
    (USER_ID = 28 AND ROLE_ID = 3) OR
    (USER_ID = 31 AND ROLE_ID = 3) OR
    (USER_ID = 34 AND ROLE_ID = 3) OR
    (USER_ID = 35 AND ROLE_ID = 3);