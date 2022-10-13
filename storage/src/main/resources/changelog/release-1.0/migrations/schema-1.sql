create table stored_file
(
    stored_file_id      bigserial       primary key,
    created_at          timestamp       default now(),
    created_by          varchar(255)    default 'system',
    modified_at         timestamp       default now(),
    modified_by         varchar(255)    default 'system',
    size_bytes          bigint          not null,
    path                varchar(255)    not null
        constraint uk_stored_file_name unique,
    version             bigint          default 0 not null
);

create table file_access
(
    file_access_id      bigserial       primary key,
    created_at          timestamp       default now(),
    created_by          varchar(255)    default 'system',
    modified_at         timestamp       default now(),
    modified_by         varchar(255)    default 'system',
    user_name           varchar(255)    not null,
    access_level        varchar(255)    not null,
    stored_file_id      bigint          not null
        constraint fk_file_access_stored_file references stored_file
);

