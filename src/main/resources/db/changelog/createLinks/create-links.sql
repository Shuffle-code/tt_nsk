--liquibase formatted sql
--changeset sergei:601944b0-6b84-47a1-848b-db6a61fea10e

INSERT INTO link_product_category (product_id, category_id) VALUES
(1, 1),
(1, 2),
(17, 2),
(20, 3)
;

--rollback DELETE FROM link_product_category;