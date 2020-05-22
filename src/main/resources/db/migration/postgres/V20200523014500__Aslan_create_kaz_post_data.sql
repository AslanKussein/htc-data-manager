create table htc_dm_kaz_post_data
(
    id     varchar(222)  not null
        primary key,
    value  varchar(1000) not null,
    status varchar(222)  not null
);

create table htc_dm_street_type
(
    id      varchar(222) not null
        primary key,
    name_en varchar(255) not null,
    name_kz varchar(255) not null,
    name_ru varchar(255) not null
);

alter table htc_dm_dic_street
    add column street_type_id varchar(222),
    add column kaz_post_id    varchar(222);

alter table htc_dm_dic_city
    add column kaz_post_id varchar(222);
alter table htc_dm_dic_district
    add column kaz_post_id varchar(222);