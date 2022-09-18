create table account
(
    account_id              bigserial           PRIMARY KEY,
    created_at              timestamp           DEFAULT now(),
    created_by              varchar(255)        DEFAULT 'system',
    modified_at             timestamp           DEFAULT now(),
    modified_by             varchar(255)        DEFAULT 'system',
    active                  boolean             DEFAULT true NOT NULL,
    email                   varchar(255)        NOT NULL,
    email_activation_state  integer             DEFAULT 0    NOT NULL,
    password                varchar(255)        NOT NULL,
    username                varchar(255)        NOT NULL
        constraint uk_gex1lmaqpg0ir5g1f5eftyaa1
            unique,
    user_id                bigint
);

create table faculty
(
    faculty_id  bigserial PRIMARY KEY,
    created_at  timestamp DEFAULT now(),
    created_by  varchar(255) DEFAULT 'system',
    modified_at timestamp DEFAULT now(),
    modified_by varchar(255) DEFAULT 'system',
    name        varchar(255)
);

create table cathedra
(
    cathedra_id bigserial
        primary key,
    created_at  timestamp DEFAULT now(),
    created_by  varchar(255) DEFAULT 'system',
    modified_at timestamp DEFAULT now(),
    modified_by varchar(255) DEFAULT 'system',
    name        varchar(255),
    faculty_id  bigint not null
        constraint fkdda292n339cqonlqlgmlg0han
            references faculty
);

create table role
(
    role_id     bigserial
        primary key,
    created_at  timestamp DEFAULT now(),
    created_by  varchar(255) DEFAULT 'system',
    modified_at timestamp DEFAULT now(),
    modified_by varchar(255) DEFAULT 'system',
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
    user_id         bigserial  primary key,
    created_at      timestamp DEFAULT now(),
    created_by      varchar(255) DEFAULT 'system',
    modified_at     timestamp DEFAULT now(),
    modified_by     varchar(255) DEFAULT 'system',
    fathers_name    varchar(255),
    first_name      varchar(255),
    last_name       varchar(255),
    type            varchar(255) not null,
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

