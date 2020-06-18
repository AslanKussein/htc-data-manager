create table htc_dm_dic_contract_type
(
  id         bigserial             not null
    primary key,
  name_en    varchar(255)          not null,
  name_kz    varchar(255)          not null,
  name_ru    varchar(255)          not null,
  code       varchar(255)          not null
    constraint uk_contract_type_code
      unique,
  is_removed boolean default false not null
);

alter table htc_dm_dic_contract_type
  owner to postgres;

comment on table htc_dm_dic_contract_type is 'Справочник типов договоров ОУ';

insert into htc_dm_dic_contract_type (id, name_en, name_kz, name_ru, code, is_removed) values (1, 'Standard', 'Стандарт', 'Стандарт', '001001', false);
insert into htc_dm_dic_contract_type (id, name_en, name_kz, name_ru, code, is_removed) values (2, 'Exclusive', 'Эксклюзив', 'Эксклюзив', '001002', false);
insert into htc_dm_dic_contract_type (id, name_en, name_kz, name_ru, code, is_removed) values (3, 'Super Exclusive', 'Супер Эксклюзив', 'Супер Эксклюзив', '001003', false);

select setval('htc_dm_dic_contract_type_id_seq', max(id)) from htc_dm_dic_contract_type;

insert into htc_dm_dic_all_dict(code, name_kz, name_ru, name_en, is_editable, is_removed)
values ('ContractType', 'Справочник типов договоров', 'Справочник типов договоров', 'Contract types', false, false);

alter table htc_dm_application_contract
  drop column is_exclusive;

alter table htc_dm_application_contract
  add column contract_type_id bigint
    constraint fk_app_contract_type
      references htc_dm_dic_contract_type;