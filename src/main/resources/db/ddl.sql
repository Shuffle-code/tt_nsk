DROP TABLE CART_PRODUCT;
DROP TABLE PRODUCT;
DROP TABLE CART;
DROP TABLE MANUFACTURER;

CREATE TABLE MANUFACTURER
(
    ID   BIGSERIAL    NOT NULL PRIMARY KEY,
    NAME VARCHAR(255) NOT NULL,
    UNIQUE (NAME),
    PRODUCT_ID BIGSERIAL NOT NULL ,
--     CONSTRAINT BOOK_PRIMARY_KEY PRIMARY KEY (ID),
    CONSTRAINT PRODUCT_FOREIGN_KEY FOREIGN KEY (PRODUCT_ID) REFERENCES PRODUCT (ID)
);

DROP TABLE MANUFACTURER;

CREATE TABLE PRODUCT
(
    ID                 BIGSERIAL      NOT NULL PRIMARY KEY,
    TITLE              VARCHAR(255)   NOT NULL,
    COST               DECIMAL(10, 2) NOT NULL,
    MANUFACTURE_DATE   DATE           NOT NULL,
    VERSION            INT            NOT NULL DEFAULT 0,
    CREATED_BY         VARCHAR(255),
    CREATED_DATE       TIMESTAMP,
    LAST_MODIFIED_BY   VARCHAR(255),
    LAST_MODIFIED_DATE TIMESTAMP,
    STATUS             VARCHAR(20)    NOT NULL DEFAULT 'ACTIVE',
        UNIQUE (TITLE)
);

CREATE TABLE CART
(
    ID     BIGSERIAL NOT NULL PRIMARY KEY,
    STATUS VARCHAR(255) DEFAULT ''
);

CREATE TABLE CONFIRMATION_CODE
(
    ID     BIGSERIAL NOT NULL PRIMARY KEY,
    CODE VARCHAR(255)
);



CREATE TABLE CART_PRODUCT
(
    CART_ID    BIGINT NOT NULL,
    PRODUCT_ID BIGINT NOT NULL,

    PRIMARY KEY (CART_ID, PRODUCT_ID),

    CONSTRAINT fk_cart_product_cart
        FOREIGN KEY (CART_ID)
            REFERENCES CART (ID),


    CONSTRAINT fk_cart_product_product
        FOREIGN KEY (PRODUCT_ID)
            REFERENCES PRODUCT (ID)
);

SELECT *
FROM cart;

ALTER TABLE PRODUCT
    ADD COLUMN VERSION            INT NOT NULL DEFAULT 0,
    ADD COLUMN CREATED_BY         VARCHAR(255),
    ADD COLUMN CREATED_DATE       TIMESTAMP,
    ADD COLUMN LAST_MODIFIED_BY   VARCHAR(255),
    ADD COLUMN LAST_MODIFIED_DATE TIMESTAMP;

ALTER TABLE PRODUCT
    ADD COLUMN STATUS VARCHAR(20) NOT NULL DEFAULT 'ACTIVE';

SELECT *
FROM PRODUCT;-- WHERE ID=106;

CREATE TABLE ACCOUNT_USER
(
    ID                      BIGSERIAL    NOT NULL PRIMARY KEY,
    username                VARCHAR(255) NOT NULL,
    password                VARCHAR(255) NOT NULL,
    firstname               VARCHAR(255) NOT NULL,
    lastname                VARCHAR(255) NOT NULL,
    account_non_expired     BOOLEAN      NOT NULL,
    account_non_locked      BOOLEAN      NOT NULL,
    credentials_non_expired BOOLEAN      NOT NULL,
    enabled                 BOOLEAN      NOT NULL,
        UNIQUE(username)
);

CREATE TABLE AUTHORITY
(
    ID         BIGSERIAL    NOT NULL PRIMARY KEY,
    permission VARCHAR(255) NOT NULL,
UNIQUE (permission)
);

CREATE TABLE ACCOUNT_ROLE
(
    id   BIGSERIAL    NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,

    UNIQUE (name)
);

CREATE TABLE USER_ROLE
(
    USER_ID BIGINT NOT NULL,
    ROLE_ID BIGINT NOT NULL,

    PRIMARY KEY (USER_ID, ROLE_ID),

    CONSTRAINT fk_user_role_account_user
        FOREIGN KEY (USER_ID)
            REFERENCES ACCOUNT_USER (ID),

    CONSTRAINT fk_user_role_account_role
        FOREIGN KEY (ROLE_ID)
            REFERENCES ACCOUNT_ROLE (ID)
);
CREATE TABLE ROLE_AUTHORITY
(
    ROLE_ID BIGINT NOT NULL,
    AUTHORITY_ID BIGINT NOT NULL,

    PRIMARY KEY (ROLE_ID, AUTHORITY_ID),

    CONSTRAINT fk_role_authority_account_role
        FOREIGN KEY (ROLE_ID)
            REFERENCES ACCOUNT_USER (ID),

    CONSTRAINT fk_role_authority_authority
        FOREIGN KEY (AUTHORITY_ID )
            REFERENCES AUTHORITY (ID)
);

CREATE TABLE CONFIRMATION_CODE
(
    ID BIGINT NOT NULL PRIMARY KEY,
    CODE VARCHAR(255) NOT NULL,
    UNIQUE (CODE),
    USER_ID BIGINT NOT NULL ,
    CONSTRAINT ACCOUNT_USER_FOREIGN_KEY FOREIGN KEY (USER_ID) REFERENCES ACCOUNT_USER (ID)

);





CREATE TABLE USER_AUTHORITY
(
    USER_ID BIGINT NOT NULL,
    AUTHORITY_ID BIGINT NOT NULL,

    PRIMARY KEY (USER_ID, AUTHORITY_ID),

    CONSTRAINT fk_user_authority_account_user
        FOREIGN KEY (USER_ID)
            REFERENCES ACCOUNT_USER (ID),

    CONSTRAINT fk_user_authority_authority
        FOREIGN KEY (AUTHORITY_ID)
            REFERENCES AUTHORITY (ID)
);
