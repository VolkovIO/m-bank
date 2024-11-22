CREATE TABLE "user"
(
    id            bigserial    NOT NULL,
    name          varchar(255) NULL,
    date_of_birth date         NULL,
    password      varchar(500) NULL,
    CONSTRAINT persons_pkey PRIMARY KEY (id)
);

create table account
(
    id      bigserial,
    user_id int8           NULL,
    balance numeric(15, 2) NULL,
    CONSTRAINT account_balance_check CHECK ((balance >= (0)::numeric)),
    CONSTRAINT account_pkey PRIMARY KEY (id),
    CONSTRAINT fk_account_user FOREIGN KEY (user_id) REFERENCES "user" (id)
);

CREATE TABLE email_data
(
    id      bigserial    NOT NULL,
    user_id int8         NULL,
    email   varchar(200) NULL,
    CONSTRAINT email_data_pkey PRIMARY KEY (id),
    CONSTRAINT email_unique UNIQUE (email),
    CONSTRAINT fk_email_data_user FOREIGN KEY (user_id) REFERENCES "user" (id)
);

CREATE TABLE phone_data
(
    id      bigserial   NOT NULL,
    user_id int8        NULL,
    phone   varchar(13) NULL,
    CONSTRAINT phone_data_pkey PRIMARY KEY (id),
    CONSTRAINT phone_unique UNIQUE (phone),
    CONSTRAINT fk_phone_data_user FOREIGN KEY (user_id) REFERENCES "user" (id)
);