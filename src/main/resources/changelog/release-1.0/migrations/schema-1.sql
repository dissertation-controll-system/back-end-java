create table account
(
    account_id             bigserial
        primary key,
    created_at             timestamp,
    created_by             varchar(255),
    modified_at            timestamp,
    modified_by            varchar(255),
    active                 boolean default true not null,
    email                  varchar(255)         not null,
    email_activation_state integer default 0    not null,
    password               varchar(255)         not null,
    username               varchar(255)         not null
        constraint uk_gex1lmaqpg0ir5g1f5eftyaa1
            unique,
    user_id                bigint
);

create table faculty
(
    faculty_id  bigserial
        primary key,
    created_at  timestamp,
    created_by  varchar(255),
    modified_at timestamp,
    modified_by varchar(255),
    name        varchar(255)
);

create table cathedra
(
    cathedra_id bigserial
        primary key,
    created_at  timestamp,
    created_by  varchar(255),
    modified_at timestamp,
    modified_by varchar(255),
    name        varchar(255),
    faculty_id  bigint not null
        constraint fkdda292n339cqonlqlgmlg0han
            references faculty
);

create table role
(
    role_id     bigserial
        primary key,
    created_at  timestamp,
    created_by  varchar(255),
    modified_at timestamp,
    modified_by varchar(255),
    name        varchar(255)
);

create table account_role
(
    account_id bigint not null
        constraint fk1f8y4iy71kb1arff79s71j0dh
            references account,
    role_id    bigint not null
        constraint fkrs2s3m3039h0xt8d5yhwbuyam
            references role
);

create table users
(
    user_id      bigserial
        primary key,
    created_at   timestamp,
    created_by   varchar(255),
    modified_at  timestamp,
    modified_by  varchar(255),
    fathers_name varchar(255),
    first_name   varchar(255),
    last_name    varchar(255),
    type         varchar(255) not null,
    account_id   bigint
        constraint fk3pwaj86pwopu3ot96qlrfo2up
            references account
);

alter table account
    add constraint fkra7xoi9wtlcq07tmoxxe5jrh4
        foreign key (user_id) references users;

create table user_cathedra
(
    user_id     bigint not null
        constraint fkf1qysfvge2olhj64dnv5fh2ny
            references users,
    cathedra_id bigint not null
        constraint fkhp9i0yy41y2pws59onv33o6l6
            references cathedra
);

