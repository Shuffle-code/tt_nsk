--liquibase formatted sql
--changeset liquibase:62deaf23-ac57-4c3a-b436-ad13ed47ebf8

INSERT INTO `role_authority`
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (2, 3),
       (3, 3),
       (1, 4),
       (1, 5),
       (3, 5),
       (1, 6),
       (2, 6),
       (2, 7),
       (3, 7);

--rollback DELETE FROM `role_authority` WHERE (ROLE_ID = 1 AND AUTHORITY_ID = 1) OR (ROLE_ID = 1 AND AUTHORITY_ID = 2) OR (ROLE_ID = 1 AND AUTHORITY_ID = 3) OR (ROLE_ID = 2 AND AUTHORITY_ID = 3) OR (ROLE_ID = 3 AND AUTHORITY_ID = 3) OR (ROLE_ID = 1 AND AUTHORITY_ID = 4) OR (ROLE_ID = 1 AND AUTHORITY_ID = 5) OR (ROLE_ID = 3 AND AUTHORITY_ID = 5) OR (ROLE_ID = 1 AND AUTHORITY_ID = 6) OR (ROLE_ID = 2 AND AUTHORITY_ID = 6) OR (ROLE_ID = 2 AND AUTHORITY_ID = 7) OR (ROLE_ID = 3 AND AUTHORITY_ID = 7);





