create table htc_dm_favorites
(
    id               bigserial    not null primary key,
    client_login     varchar(255) not null,
    real_property_id bigint       not null,
    create_date      TIMESTAMP    not null default now()
);

comment on table htc_dm_favorites is 'Избранные объекты';