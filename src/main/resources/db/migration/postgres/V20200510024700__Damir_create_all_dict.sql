create table htc_dm_dic_all_dict
(
  id         bigserial             not null primary key,
  name_en    varchar(255)          not null,
  name_kz    varchar(255)          not null,
  name_ru    varchar(255)          not null,
  code       varchar(255)          not null
    constraint fk_al_dict_unique
      unique,
  is_editable boolean default true  not null,
  is_system boolean default true  not null,
  is_enabled boolean default true  not null,
  is_removed boolean default false not null
);

comment on table htc_dm_dic_all_dict is 'Справочник всех справочников';

insert into htc_dm_dic_all_dict(code, name_kz, name_ru, name_en, is_editable, is_system, is_enabled, is_removed)
values ('ApplicationStatus', 'Справочник статусов заявки', 'Справочник статусов заявки', 'Справочник статусов заявки', false, true, true, false);

insert into htc_dm_dic_all_dict(code, name_kz, name_ru, name_en, is_editable, is_system, is_enabled, is_removed)
values ('Street', 'Справочник улиц', 'Справочник улиц', 'Справочник улиц', true, false, true, false);

alter table htc_dm_dic_street
  add column district_id bigint
    constraint fk_street_district
    references htc_dm_dic_district;