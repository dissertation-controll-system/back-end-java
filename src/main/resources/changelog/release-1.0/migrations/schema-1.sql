CREATE TABLE account
(
    account_id              bigserial PRIMARY KEY,
    created_at              timestamp,
    created_by              varchar(255),
    modified_at             timestamp,
    modified_by             varchar(255),
    active                  boolean DEFAULT false NOT NULL,
    email                   varchar(255)          NOT NULL,
    email_activation_state  integer DEFAULT 0     NOT NULL,
    password                varchar(255)          NOT NULL,
    username                varchar(255)          NOT NULL,
    user_id                 bigint
);

CREATE TABLE role
(
    role_id                 bigserial PRIMARY KEY,
    created_at              timestamp,
    created_by              varchar(255),
    modified_at             timestamp,
    modified_by             varchar(255),
    name                    varchar(255)
);

CREATE TABLE account_role
(
    account_id              bigint NOT NULL CONSTRAINT fk_account_role_account REFERENCES account,
    student_id              bigint NOT NULL CONSTRAINT fk_account_role_role REFERENCES role
);

CREATE TABLE users
(
    user_id                 bigserial PRIMARY KEY,
    created_at              timestamp,
    created_by              varchar(255),
    modified_at             timestamp,
    modified_by             varchar(255),
    fathers_name            varchar(255),
    first_name              varchar(255),
    last_name               varchar(255),
    type                    varchar(255) NOT NULL,
    account_id              bigint CONSTRAINT fk_users_account REFERENCES account
);

ALTER TABLE account ADD CONSTRAINT fk_account_user FOREIGN KEY (user_id) REFERENCES users;

CREATE TABLE faculty
(
    faculty_id              bigserial PRIMARY KEY,
    created_at              timestamp,
    created_by              varchar(255),
    modified_at             timestamp,
    modified_by             varchar(255),
    name                    varchar(255)
);

CREATE TABLE cathedra
(
    cathedra_id             bigserial PRIMARY KEY,
    created_at              timestamp,
    created_by              varchar(255),
    modified_at             timestamp,
    modified_by             varchar(255),
    name                    varchar(255),
    faculty_id              bigint NOT NULL CONSTRAINT fk_cathedra_faculty REFERENCES faculty
);

CREATE TABLE user_cathedra
(
    user_id                 bigint NOT NULL CONSTRAINT fk_users_cathedra_users REFERENCES users,
    cathedra_id             bigint NOT NULL CONSTRAINT fk_users_cathedra_cathedra REFERENCES cathedra
);

