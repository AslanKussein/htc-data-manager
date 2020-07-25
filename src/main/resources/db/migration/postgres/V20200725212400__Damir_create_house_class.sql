create table htc_dm_dic_house_class
(
  id         bigserial             not null
    primary key,
  name_en    varchar(255)          not null,
  name_kz    varchar(255)          not null,
  name_ru    varchar(255)          not null,
  is_removed boolean default false not null
);

comment on table htc_dm_dic_house_class is 'Справочник класс недвижимости';

insert into htc_dm_dic_house_class (name_en, name_kz, name_ru, is_removed)
values ('Эконом', 'Эконом', 'Эконом', false);

insert into htc_dm_dic_house_class (name_en, name_kz, name_ru, is_removed)
values ('Комфорт', 'Комфорт', 'Комфорт', false);

insert into htc_dm_dic_house_class (name_en, name_kz, name_ru, is_removed)
values ('Бизнес', 'Бизнес', 'Бизнес', false);

insert into htc_dm_dic_house_class (name_en, name_kz, name_ru, is_removed)
values ('Элит', 'Элит', 'Элит', false);

insert into htc_dm_dic_all_dict(code, name_kz, name_ru, name_en, is_editable, is_removed)
values ('HouseClass', 'Класс жилья', 'Класс жилья', 'House Class', true, false);

alter table htc_dm_general_characteristics
  add column house_class_id bigint
    constraint fk_gen_characteristics_house_class
      references htc_dm_dic_house_class;

create table htc_dm_real_property_analytics
(
  id bigserial not null primary key,
  average_price numeric(10,2) not null,
  building_id bigint null,
  district_id bigint null,
  house_class_id bigint null
);

comment on table htc_dm_real_property_analytics is 'Таблица аналитики цены по ЖК или району';