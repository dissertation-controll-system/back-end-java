create table users
(
    user_id      bigserial primary key,
    created_at   timestamp default now(),
    created_by   varchar(255) default 'system',
    modified_at  timestamp default now(),
    modified_by  varchar(255) default 'system',
    fathers_name varchar(255),
    first_name   varchar(255),
    last_name    varchar(255),
    type         varchar(255) not null
);

create table organization (
    organization_id     bigserial           primary key,
    created_at          timestamp           default now(),
    created_by          varchar(255)        default 'system',
    modified_at         timestamp           default now(),
    modified_by         varchar(255)        default 'system',
    head_id             bigint
     constraint fk_organization_head references users,
    name                varchar(255)        not null,
    owner_id            bigint
      constraint fk_organization_owner references organization
);

create table account
(
    account_id             bigserial primary key,
    created_at             timestamp default now(),
    created_by             varchar(255)  default 'system',
    modified_at            timestamp default now(),
    modified_by            varchar(255) default 'system',
    active                 boolean default true not null,
    email                  varchar(255)         not null,
    email_activation_state integer default 0    not null,
    password               varchar(255)         not null,
    username               varchar(255)         not null
        constraint uk_account_username unique,
    user_id                bigint
        constraint uk_account_user_id unique
        constraint fk_account_users references users
);

create table role
(
    role_id     bigserial primary key,
    created_at  timestamp default now(),
    created_by  varchar(255) default 'system',
    modified_at timestamp default now(),
    modified_by varchar(255) default 'system',
    name        varchar(255)
);

create table account_role
(
    account_id bigint not null
        constraint fk_account_role_account references account,
    role_id    bigint not null
        constraint fk_account_role_role references role,
    primary key (account_id, role_id)
);

create table app_user_organization_unit (
    organization_unit_organization_id       bigserial           not null
        constraint fk_organization_participants_organization references organization,
    user_user_id                            bigserial           not null
        constraint fk_organization_participants_participant references users,
    role_id                                 bigserial           not null
        constraint fk_organization_participants_role references role
);
