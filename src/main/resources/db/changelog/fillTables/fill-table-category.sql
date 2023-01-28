--liquibase formatted sql
--changeset sergei:44ec8941-9a5e-473f-9173-550969d72d2c

INSERT INTO category (category_name) VALUES
('Пишущие принадлежности'),
('Принадлежности для рисования'),
('Чертежные инструменты')
;

--rollback DROP TABLE category;